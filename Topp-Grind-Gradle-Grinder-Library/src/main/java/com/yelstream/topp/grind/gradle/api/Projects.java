package com.yelstream.topp.grind.gradle.api;

import com.yelstream.topp.format.util.PropertiesFormatter;
import lombok.experimental.UtilityClass;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import java.util.Map;

/**
 * Utilities addressing instance of {@link Project}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@UtilityClass
public class Projects {
    /**
     * Gets all project properties.
     * @param project Project.
     * @return Project properties.
     */
    public static Map<String,Object> getProjectProperties(Project project) {
        @SuppressWarnings("unchecked")
        Map<String,Object> projectProperties=(Map<String,Object>)project.getProperties();
        logProjectProperties(project,projectProperties);
        return projectProperties;
    }

    /**
     * Logs project properties.
     * @param project Project.
     * @param projectProperties Project properties.
     * @param <V> Type of project property value.
     */
    public static <V> void logProjectProperties(Project project,
                                                Map<String,V> projectProperties) {
        Logger logger=project.getLogger();
        if (logger.isInfoEnabled()) {
            PropertiesFormatter formatter=PropertiesFormatter.builder().build();
            String formattedProjectProperties=formatter.format(projectProperties);
            logger.info(String.format("Project properties are:%n%s",formattedProjectProperties));
        }
    }
}
