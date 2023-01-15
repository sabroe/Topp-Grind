package com.yelstream.topp.format.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Formatter of properties.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class PropertiesFormatter {
    /**
     * Default value for line prefix.
     */
    public static final String DEFAULT_LINE_PREFIX="    ";

    /**
     * Default value for showing indexes.
     */
    public static final boolean DEFAULT_SHOW_INDEX=true;

    /**
     * Default value for alignment of indexes.
     */
    public static final boolean DEFAULT_ALIGN_INDEXES=true;

    /**
     * Default value for the index separator.
     */
    public static final String DEFAULT_INDEX_SEPARATOR=") ";

    /**
     * Default value for the alignment of keys.
     */
    public static final boolean DEFAULT_ALIGN_KEYS=true;

    /**
     * Default value for the key-value separator.
     */
    public static final String DEFAULT_KEY_VALUE_SEPARATOR=" = ";

    /**
     * Default value for the sorting by key.
     */
    public static final boolean DEFAULT_SORT_BY_KEY=true;

    /**
     * Line prefix.
     * This is a possible indentation of each line.
     */
    @lombok.Builder.Default
    private final String linePrefix=DEFAULT_LINE_PREFIX;

    /**
     * Indicates, if line indexes 1, 2, 3, ... should be generated.
     */
    @lombok.Builder.Default
    private final boolean showIndex=DEFAULT_SHOW_INDEX;

    /**
     * Indicates, if line indexes 1, 2, 3, ... should be generated.
     */
    @lombok.Builder.Default
    private final boolean alignIndexes=DEFAULT_ALIGN_INDEXES;

    /**
     * Key-value separator.
     */
    @lombok.Builder.Default
    private final String keyValueSeparator=DEFAULT_KEY_VALUE_SEPARATOR;

    /**
     * Index separator.
     * Separator between the index and the rest of the line with the key-value part shown.
     */
    @lombok.Builder.Default
    private final String indexSeparator=DEFAULT_INDEX_SEPARATOR;

    /**
     * Indicates, if keys should be aligned.
     */
    @lombok.Builder.Default
    private final boolean alignKeys=DEFAULT_ALIGN_KEYS;

    /**
     * Indicates, if lines should be sorted by key.
     */
    @lombok.Builder.Default
    private final boolean sortByKey=DEFAULT_SORT_BY_KEY;

    /**
     * Formats properties.
     * @param properties Properties.
     * @param <V> Type of property value.
     * @return Formatted properties.
     */
    public <V> String format(Map<String,V> properties) {
        StringBuilder sb=new StringBuilder();

        if (properties!=null) {
            String indexFormat = createIndexFormat(properties,showIndex,alignIndexes,indexSeparator);
            String keyValueFormat = createKeyValueFormat(properties,alignKeys, keyValueSeparator);
            String lineFormat = "%s%s%s";

            Map<String,V> sortedProperties=sortByKey?sortByKey(properties):properties;

            final int[] index = {1};
            sortedProperties.forEach((key, value) -> {
                if (!sb.isEmpty()) {
                    sb.append(String.format("%n"));
                }
                String formattedPrefix=String.format(indexFormat, index[0]);
                String formattedKeyValue=String.format(keyValueFormat, key, value);
                String formattedLine=String.format(lineFormat, linePrefix, formattedPrefix, formattedKeyValue);
                sb.append(formattedLine);
                index[0]++;
            });
        }

        return sb.toString();
    }

    /**
     * Creates the format for formatting indexes.
     * @param properties Properties.
     * @param showIndex Indicates, if the index should be shown.
     * @param alignIndexes Indicates, if indexes should be aligned.
     * @param indexSeparator Separator between indexes and the key-value part of lines.
     * @return Format.
     */
    private static <V> String createIndexFormat(Map<String,V> properties,
                                                boolean showIndex,
                                                boolean alignIndexes,
                                                String indexSeparator) {
        String format="";
        if (showIndex) {
            String fieldLength="";
            if (alignIndexes) {
                int size = properties.size();
                fieldLength = Integer.toString(Integer.toString(size).length());
            }

            format="%"+fieldLength+"d"+indexSeparator;
        }
        return format;
    }

    /**
     * Creates the format for formatting (key,value) property pair.s
     * @param properties Properties.
     * @param alignKeys Indicates, if keys should be aligned.
     * @param keyValueSeparator Separator between keys and values.
     * @return Format.
     */
    private static <V> String createKeyValueFormat(Map<String,V> properties,
                                                   boolean alignKeys,
                                                   String keyValueSeparator) {
        int fieldLength=0;
        if (alignKeys) {
            fieldLength=properties.keySet().stream().map(String::length).max(Integer::compare).orElse(0);
        }
        return "%1$-"+fieldLength+"s"+keyValueSeparator+"%2$s";
    }

    /**
     * Sorts properties by key.
     * @param properties Properties.
     * @return Sorted properties.
     */
    private static <V> SortedMap<String,V> sortByKey(Map<String,V> properties) {
        return sortByKey(properties,String::compareTo);
    }

    /**
     * Sorts properties by key.
     * @param properties Properties.
     * @param comparator Comparator for the ordering of keys.
     * @return Sorted properties.
     */
    private static <K,V> SortedMap<K,V> sortByKey(Map<K,V> properties,
                                                  Comparator<? super K> comparator) {
        TreeMap<K,V> sortedProperties=null;
        if (properties!=null) {
            sortedProperties=new TreeMap<>(comparator);
            sortedProperties.putAll(properties);
        }
        return sortedProperties;
    }
}
