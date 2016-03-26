package com.biggerbytes.scheduleupdates;

/**
 * TODO use enum instead
 * @author Avishay
 */
public interface CommandConstants {
    
    // HEADERS 
    final byte SERVER_HEADER = 0x5F;
    final byte SCEHDULES_HEADER = 0x6F;
    
    // SERVER FLAGS    
    final byte SET_SERVER_STATE = 0x50; // not sure if needed..
    
    // SCHEDULES FLAGS    
    final byte SET_INFO_THREAD_STATE = 0x60; // Syntax: {HEADER} {FLAG} {STATE} ;; STATE: ON is 1, OFF is everything else
    final byte SET_REFRESH_RATE = 0x61; // Syntax: {HEADER} {FLAG} {MINUTES} ;; where MINUTES is a byte that valued between 0 - 255
    final byte READ_INFO_NOW = 0x62; // Syntax: {HEADER} {FLAG}
    final byte ADD_CANCEL_DUMMY = 0x63; // Syntax: {HEADER} {FLAG} {CLASS_ID} {HOUR} {DATE} {TEACHER_NAME} ;; HOUR is max 2 bytes (3 - 5), DATE is always 10 bytes (5 - 15), TEACHER_NAME has unknown amount of bytes (15 - ?)
    final byte ADD_SUB_DUMMY = 0x64; // same^ but teacher_name will be sub name
    final byte REMOVE_ALL_DUMMIES_FROM_ID = 0x65; // Syntax: {HEADER} {FLAG} {CLASS_ID}
    
}
