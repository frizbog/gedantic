/*
 * Copyright (c) 2016 Matthew R. Harrah
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.gedantic.web.taglib;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

/**
 * Functions to do introspection-type activity on beans, to allow dynamic handling from JSTL
 * 
 * @author frizbog
 */
public class ClassTagFunctions {

    /**
     * Does an object have a property with the supplied name?
     * 
     * @param o
     *            the object
     * @param propertyName
     *            the property name
     * @return true iff the object has a property with the supplied name
     */
    public static boolean hasProperty(Object o, String propertyName) {
        if (o == null || propertyName == null) {
            return false;
        }

        BeanInfo beanInfo;
        try {
            beanInfo = java.beans.Introspector.getBeanInfo(o.getClass());
        }

        catch (@SuppressWarnings("unused") IntrospectionException ignored) {
            return false;
        }

        for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {

            if (propertyName.equals(pd.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if the supplied object is an instance of a specific class (by name)
     * 
     * @param o
     *            the object
     * @param className
     *            the className
     * @return if the supplied object is an instance of the specified class
     */
    public static boolean instanceOf(Object o, String className) {
        if (o == null || className == null) {
            return false;
        }

        try {
            return Class.forName(className, false, Thread.currentThread().getContextClassLoader()).isInstance(o);
        }

        catch (@SuppressWarnings("unused") ClassNotFoundException ignored) {
            return false;
        }
    }
}
