package com.biggerbytes.scheduleupdates;

import static com.biggerbytes.scheduleupdates.Main.dummyChanges;
import com.biggerbytes.scheduleupdates.serverdatarecievers.ScheduleChange;
import java.util.Arrays;

/**
 * A class that will get an array of bytes and executes the encrypted command
 * @author Avishay
 */
public class CommandProcessor {
    public static void executeCommand(byte[] command) {
        switch (command[0]) {
            case CommandConstants.SERVER_HEADER:
                // TODO
                break;
            
            case CommandConstants.SCEHDULES_HEADER:
                switch (command[1]) {
                    case CommandConstants.SET_REFRESH_RATE: 
                        Integer minutes = (int) command[2];
                        setRefreshInterval(minutes);
                        break;
                        
                    case CommandConstants.SET_INFO_THREAD_STATE:
                        Boolean state = command[2] == 0x01;
                        setThreadState(state);
                        break;
                        
                    case CommandConstants.READ_INFO_NOW:
                        refreshDataNow();
                        break;
                    
                    case CommandConstants.ADD_CANCEL_DUMMY:
                        try {
                        ScheduleChange dummy = new ScheduleChange(new String(Arrays.copyOfRange(command, 5, 15)),
                                                                  new String(Arrays.copyOfRange(command, 3, 5)),
                                                                  new String(Arrays.copyOfRange(command, 15, command.length), "UTF-8")); 
                        if (dummy.getHour().startsWith("0")) //meh
                            dummy.setHour(dummy.getHour().substring(1));
                        addDummy(dummy, (int) command[2]);
                        } catch (Exception e) {
                            alertFailure();
                            e.printStackTrace();
                        }
                        break;
                        
                    case CommandConstants.ADD_SUB_DUMMY:
                        try {
                            ScheduleChange dummy = new ScheduleChange(new String(Arrays.copyOfRange(command, 5, 15)),
                                                    new String(Arrays.copyOfRange(command, 3, 5)),
                                                    "");
                            dummy.setSubTeacher(new String(Arrays.copyOfRange(command,15, command.length), "UTF-8"));
                            if (dummy.getHour().startsWith("0")) //meh2
                                dummy.setHour(dummy.getHour().substring(1));
                            addDummy(dummy, (int) command[2]);
                        } catch (Exception e) {
                            alertFailure();
                            e.printStackTrace();
                        }
                        break;
                        
                    case CommandConstants.REMOVE_ALL_DUMMIES_FROM_ID:
                        removeDummiesFromID((int) command[2]);
                        break;
                }
                break;
        }
    }
    
    private static void setThreadState(Boolean state) {
        Main.setThreadState(state);
    }
    
    private static void setRefreshInterval(Integer minutes) {
        if (minutes == 0)
            alertFailure();
        else
            Main.setRefreshDelay(minutes * 1000l * 60l);
    }
    
    private static void refreshDataNow() {
        try {
            DataFactory.loadData();
        } catch (Exception e) {
            alertFailure();
            e.printStackTrace();
        }
    }
    
    private static void addDummy(ScheduleChange change, Integer classID) {     
        ScheduleChange[] changes;
        if (dummyChanges.get(classID) == null)
            changes = new ScheduleChange[0];
        else  
            changes = dummyChanges.get(classID);
        
        ScheduleChange[] changes_ = new ScheduleChange[changes.length + 1];
        int index = 0;
        for (ScheduleChange change_ : changes) {
            changes_[index] = change_;
            index++;
        }
       changes_[index] = change;
       
       dummyChanges.put(classID, changes_);
    }
    
    private static void removeDummiesFromID(Integer classID) {
        dummyChanges.put(classID, null);
    }
    
    private static void alertFailure() {
        // TODO
    }
}
