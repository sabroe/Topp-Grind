package com.yelstream.topp.grind.gradle.api;

import com.yelstream.topp.format.util.PropertiesFormatter;
import lombok.experimental.UtilityClass;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utilities addressing instance of {@link Task}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@UtilityClass
public class Tasks {
    /**
     * Gets all task properties.
     * <p>
     * Note these properties are ad hoc-properties fed with {@code -P<task-name>:value} and not the usual way
     * using {@link org.gradle.api.tasks.options.Option} and {@link org.gradle.api.tasks.options.OptionValues}.
     * </p>
     * @param task Task.
     * @return Task properties.
     */
    public static Map<String,Object> getTaskProperties(Task task) {
        Project project=task.getProject();
        Map<String,Object> projectProperties=Projects.getProjectProperties(project);
        return getTaskProperties(task,projectProperties);
    }

    /**
     * Gets all task properties.
     * <p>
     * Note these properties are ad hoc-properties fed with {@code -P<task-name>:value} and not the usual way
     * using {@link org.gradle.api.tasks.options.Option} and {@link org.gradle.api.tasks.options.OptionValues}.
     * </p>
     * @param task Task.
     * @param projectProperties Project properties.
     * @return Task properties.
     */
    public static Map<String,Object> getTaskProperties(Task task,
                                                       Map<String,Object> projectProperties) {
        Map<String,Object> taskProperties=new HashMap<>();

        if (projectProperties!=null) {
            String taskKeyPrefix=getTaskPropertyKeyPrefix(task);

            projectProperties.forEach((key, value) -> {
                if (key.startsWith(taskKeyPrefix)) {
                    taskProperties.put(key.substring(taskKeyPrefix.length()), value);
                }
            });
        }
        logTaskProperties(task,taskProperties);

        return taskProperties;
    }

    /**
     * Logs task properties.
     * @param task Task.
     * @param taskProperties Task properties.
     * @param <V> Type of task property value.
     */
    public static <V> void logTaskProperties(Task task,
                                             Map<String,V> taskProperties) {
        Project project=task.getProject();
        Logger logger=project.getLogger();
        if (logger.isInfoEnabled()) {
            String taskKeyPrefix= getTaskPropertyKeyPrefix(task);
            PropertiesFormatter formatter=PropertiesFormatter.builder().build();
            String formattedTaskProperties=formatter.format(taskProperties);
            logger.info(String.format("Task properties are (project properties with keys with prefix '%s'):%n%s",taskKeyPrefix,formattedTaskProperties));
        }
    }

    /**
     * Gets the prefix for ad hoc-properties for a task.
     * @param task Task
     * @return Prefix.
     */
    public static String getTaskPropertyKeyPrefix(Task task) {
        String taskName=task.getName();
        return taskName+":";
    }

    /**
     * Log a "hello" to the Gradle logger.
     * This may be used to indicate the presence of a task and it being run.
     * @param task Task task.
     */
    public static void logHello(Task task) {
        Project project=task.getProject();
        Logger logger=project.getLogger();
        if (logger.isInfoEnabled()) {
            String taskName=task.getName();
            logger.info(String.format("Hello, %s!",taskName));
        }
    }

    /**
     * Verifies that the properties for a task a valid.
     * @param task Task.
     * @param validTaskPropertyKeys Valid task property keys.
     */
    public static void verifyTaskPropertyKeys(Task task,
                                              Set<String> validTaskPropertyKeys) {
        Map<String,Object> taskProperties=getTaskProperties(task);
        verifyTaskPropertyKeys(task,taskProperties,validTaskPropertyKeys);
    }

    /**
     * Verifies that the properties for a task a valid.
     * @param task Task.
     * @param taskProperties Task properties.
     * @param validTaskPropertyKeys Valid task property keys.
     */
    public static void verifyTaskPropertyKeys(Task task,
                                              Map<String,Object> taskProperties,
                                              Set<String> validTaskPropertyKeys) {
        Set<String> taskPropertyKeys=taskProperties.keySet();
        verifyTaskPropertyKeys(task,taskPropertyKeys,validTaskPropertyKeys);
    }

    /**
     * Verifies that the properties for a task a valid.
     * @param task Task.
     * @param taskPropertyKeys Task property keys.
     * @param validTaskPropertyKeys Valid task property keys.
     */
    public static void verifyTaskPropertyKeys(Task task,
                                              Set<String> taskPropertyKeys,
                                              Set<String> validTaskPropertyKeys) {
        if (!validTaskPropertyKeys.containsAll(taskPropertyKeys)) {
            List<String> invalidTaskPropertyKeys=taskPropertyKeys.stream().filter(key->!validTaskPropertyKeys.contains(key)).toList();
            throw new IllegalArgumentException(String.format("Failure to recognize task property keys; task name is '%s', actual task property keys are %s, valid task property keys are %s, invalid task property keys are %s!",
                    task.getName(),taskPropertyKeys,validTaskPropertyKeys,invalidTaskPropertyKeys));
        }
    }
}
