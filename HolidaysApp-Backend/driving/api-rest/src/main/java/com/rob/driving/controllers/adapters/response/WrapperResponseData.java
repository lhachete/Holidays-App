package com.rob.driving.controllers.adapters.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrapperResponseData<T> {
    private T data;
}
