package com.example.telegrambotweather.Model;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Data
@Component

public class History<T> implements Iterable<T>{

    private final int maxSize=3;

    @Getter
    private LinkedHashSet<T> history=new LinkedHashSet<>();

    public void add(T item) {
        if (history.contains(item)) {
            history.remove(item); // Видаляємо елемент, щоб перемістити його в кінець
        } else if (history.size() >= maxSize) {
            Iterator<T> it = history.iterator();
            it.next(); // Переходимо до найстарішого елемента
            it.remove(); // Видаляємо найстаріший елемент
        }
        history.add(item); // Додаємо новий елемент до кінця
    }


    @Override
    public Iterator<T> iterator() {
        return history.iterator();
    }
}
