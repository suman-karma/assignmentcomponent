/*
 * Enum.java
 *
 * Created on June 6, 2003, 7:56 AM
 */
package io.mhe.assignmentcomponent.vo;

import java.io.Serializable;

/**
 *
 * @author  Kameshwar
 */
public abstract class Enum implements Serializable{
    private String value;

    /** Creates a new instance of Enum */
    public Enum() {
    }

    protected Enum(String data) {
        this.value = data;
    }

    public String getValue() {
        return this.value;
    }

    protected static boolean allowed( String data , String[] pValues) {
        for( int i = 0; i < pValues.length; i++ ) {
            if( data.equals( pValues[ i ] ) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj){
        if( this == obj) { 
        	return true;
        }
        if( obj != null ){
            if( obj.getClass().equals(this.getClass())) {
                Enum enum1 = (Enum) obj;
                return this.getValue().equals(enum1.getValue());
            }
            return false;
        }
        return false;
	}

    @Override
	public int hashCode() {
		return (this.value + "").hashCode();
	}
}