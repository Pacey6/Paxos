/*
 * MIT License
 *
 * Copyright (c) 2021 Pacey Lau
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.pacey6.paxos.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by pacey6 on 3/23/21.
 * Homepage:https://pacey6.com/
 * Mail:support@pacey6.com
 */
public class ResolvableClass extends ResolvableType<Class<?>> {
    public ResolvableClass(Class<?> type) {
        super(type);
    }

    @Override
    public boolean equalsClass(Class<?> clazz) {
        return getType().equals(clazz);
    }

    @Override
    public ResolvableType<? extends Type> getSuperType() {
        Type genericSuperclass = getType().getGenericSuperclass();
        if (null == genericSuperclass) {
            return null;
        }
        if (genericSuperclass instanceof Class<?>) {
            return new ResolvableClass((Class<?>) genericSuperclass);
        }
        if (genericSuperclass instanceof ParameterizedType) {
            return new ResolvableParameterizedType((ParameterizedType) genericSuperclass);
        }
        throw new ClassCastException();
    }
}
