package com.sillimfive.mymap.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class ResultSet<T> {

    private Error error;
    private T data;

    private String result; //SUCCESS, ERROR

    @Builder
    public ResultSet(Error error, T data, String result) {
        this.error = error;
        this.data = data;
        this.result = result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultSet<?> resultSet = (ResultSet<?>) o;
        return Objects.equals(error, resultSet.error) && Objects.equals(data, resultSet.data) && Objects.equals(result, resultSet.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, data, result);
    }
}
