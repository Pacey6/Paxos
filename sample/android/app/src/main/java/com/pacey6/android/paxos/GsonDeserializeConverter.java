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

package com.pacey6.android.paxos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.pacey6.paxos.converter.Converter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author Pacey Lau
 */
public class GsonDeserializeConverter<T> implements Converter<String, T> {
    @NonNull
    private final Gson gson;
    @NonNull
    private final TypeAdapter<T> typeAdapter;

    public GsonDeserializeConverter(@NonNull Gson gson, @NonNull TypeAdapter<T> typeAdapter) {
        this.gson = gson;
        this.typeAdapter = typeAdapter;
    }

    @Nullable
    @Override
    public T convert(@Nullable String value) throws IOException {
        Reader reader = new StringReader(value);
        JsonReader jsonReader = gson.newJsonReader(reader);
        T result = typeAdapter.read(jsonReader);
        jsonReader.close();
        return result;
    }
}
