/*
 * Dumbster - a dummy SMTP server
 * Copyright 2004 Jason Paul Kitchen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dumbster.smtp;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Container for a complete SMTP message - headers and message body.
 */
public class MailMessageImpl implements MailMessage {
    private Map<String, List<String>> headers;
    private StringBuffer body;

    public MailMessageImpl() {
        headers = new HashMap<String, List<String>>(10);
        body = new StringBuffer();
    }

    @Override
    public Iterator<String> getHeaderNames() {
        Set<String> nameSet = headers.keySet();
        return nameSet.iterator();
    }

    @Override
    public String[] getHeaderValues(String name) {
        List<String> values = headers.get(name);
        if (values == null) {
            return new String[0];
        } else {
            return values.toArray(new String[headers.size()]);
        }
    }

    @Override
    public String getFirstHeaderValue(String name) {
        List<String> values = headers.get(name);
        if (values == null) {
            return null;
        } else {
            Iterator<String> iterator = values.iterator();
            return iterator.next();
        }
    }

    @Override
    public String getBody() {
        return body.toString();
    }

    @Override
    public void addHeader(String name, String value) {
        List<String> valueList = headers.get(name);
        if (valueList == null) {
            valueList = new ArrayList<String>(1);
        }
        valueList.add(value);
        headers.put(name, valueList);
    }

    @Override
    public void appendBody(String line) {
        body.append(line);
    }

    public String toString() {
        StringBuffer msg = new StringBuffer();
        for (Iterator<String> i = headers.keySet().iterator(); i.hasNext();) {
            String name = i.next();
            List<String> values = headers.get(name);
            for (Iterator<String> j = values.iterator(); j.hasNext();) {
                String value = j.next();
                msg.append(name);
                msg.append(": ");
                msg.append(value);
                msg.append('\n');
            }
        }
        msg.append('\n');
        msg.append(body);
        msg.append('\n');
        return msg.toString();
    }
}
