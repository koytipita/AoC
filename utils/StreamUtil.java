package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamUtil {
    public static <T> ArrayList<T>
    getArrayListFromStream(Stream<T> stream)
    {
        List<T>list = stream.toList();
        return new ArrayList<T>(list);
    }
}
