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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pacey6.paxos.basic.Instance;

/**
 * @author Pacey Lau
 */
public class PaxosDatabase {
    private static final String DATABASE_NAME = "paxos.db";

    private final SQLiteDatabase database;

    public PaxosDatabase(@NonNull Context context) {
        this.database = context.getApplicationContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        this.database.execSQL("CREATE TABLE IF NOT EXISTS `instance`(`question` TEXT NOT NULL PRIMARY KEY, `promised_number` INTEGER, `accepted_number` INTEGER, `accepted_answer` TEXT)");
    }

    public void insertOrUpdateInstance(@NonNull Instance<String, String> instance) {
        ContentValues values = new ContentValues();
        values.put("promised_number", instance.getPromisedNumber());
        values.put("accepted_number", instance.getAcceptedNumber());
        values.put("question", instance.getQuestion());
        values.put("accepted_answer", instance.getAcceptedAnswer());
        database.insertWithOnConflict("instance", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Nullable
    public Instance<String, String> queryInstance(@NonNull String question) {
        Cursor cursor = database.rawQuery("SELECT * FROM `instance` WHERE `question` = ?", new String[]{question});
        if (cursor.moveToNext()) {
            Instance<String, String> instance = new Instance<>();
            instance.setPromisedNumber(cursor.getInt(cursor.getColumnIndex("promised_number")));
            instance.setAcceptedNumber(cursor.getInt(cursor.getColumnIndex("accepted_number")));
            instance.setQuestion(cursor.getString(cursor.getColumnIndex("question")));
            instance.setAcceptedAnswer(cursor.getString(cursor.getColumnIndex("accepted_answer")));
            cursor.close();
            return instance;
        } else {
            cursor.close();
            return null;
        }
    }
}
