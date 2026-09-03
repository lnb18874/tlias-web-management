package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOption<T, E> {
//    泛型写法
    private List<T> jobList;
    private List<E> dataList;
}
