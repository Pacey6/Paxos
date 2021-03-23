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
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by pacey6 on 3/23/21.
 * Homepage:https://pacey6.com/
 * Mail:support@pacey6.com
 */
public class ResolvableParameterizedType extends ResolvableType<ParameterizedType> {
    public ResolvableParameterizedType(ParameterizedType type) {
        super(type);
    }

    public ResolvableParameterizedType(Type rawType, Type[] actualTypeArguments) {
        super(new ParameterizedTypeImpl(null, rawType, actualTypeArguments));
    }

    public ResolvableParameterizedType(Type ownerType, Type rawType, Type[] actualTypeArguments) {
        super(new ParameterizedTypeImpl(ownerType, rawType, actualTypeArguments));
    }

    @Override
    public boolean equalsClass(Class<?> clazz) {
        return getType().getRawType().equals(clazz);
    }

    @Override
    public ResolvableType<? extends Type> getSuperType() {
        ParameterizedType type = getType();
        Type rawType = type.getRawType();
        if (rawType instanceof Class<?>) {
            Class<?> rawClass = (Class<?>) rawType;
            Type genericSuperclass = rawClass.getGenericSuperclass();
            if (null == genericSuperclass) {
                return null;
            }
            if (genericSuperclass instanceof Class<?>) {
                return new ResolvableClass((Class<?>) genericSuperclass);
            }
            if (genericSuperclass instanceof ParameterizedType) {
                ParameterizedType superType = (ParameterizedType) genericSuperclass;
                TypeVariable<? extends Class<?>>[] typeParameters = rawClass.getTypeParameters();
                Type[] actualTypeArguments = type.getActualTypeArguments();
                Type[] superActualTypeArguments = superType.getActualTypeArguments();
                for (int i = 0; i < superActualTypeArguments.length; i++) {
                    for (int j = 0; j < typeParameters.length; j++) {
                        if (superActualTypeArguments[i].toString().equals(typeParameters[j].getName())) {
                            superActualTypeArguments[i] = actualTypeArguments[j];
                            break;
                        }
                    }
                }
                return new ResolvableParameterizedType(superType.getOwnerType(), superType.getRawType(), superActualTypeArguments);
            }
        }
        throw new ClassCastException();
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] actualTypeArguments;

        private ParameterizedTypeImpl(Type ownerType, Type rawType, Type[] actualTypeArguments) {
            this.ownerType = ownerType;
            this.rawType = rawType;
            this.actualTypeArguments = actualTypeArguments;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return actualTypeArguments;
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(actualTypeArguments)
                    ^ rawType.hashCode()
                    ^ hashCodeOrZero(ownerType);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) obj;
            return Objects.equals(getOwnerType(), parameterizedType.getOwnerType())
                    && getRawType().equals(parameterizedType.getRawType())
                    && Arrays.equals(getActualTypeArguments(), parameterizedType.getActualTypeArguments());
        }

        @Override
        public String toString() {
            int length = actualTypeArguments.length;
            if (length == 0) {
                return typeToString(rawType);
            }

            StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
            stringBuilder.append(typeToString(rawType)).append("<").append(typeToString(actualTypeArguments[0]));
            for (int i = 1; i < length; i++) {
                stringBuilder.append(", ").append(typeToString(actualTypeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }

        private int hashCodeOrZero(Object o) {
            return o != null ? o.hashCode() : 0;
        }

        private String typeToString(Type type) {
            return type instanceof Class ? ((Class<?>) type).getName() : type.toString();
        }
    }
}
