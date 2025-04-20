package app;

import java.util.ArrayList;
import java.util.List;

public class Diary {

    private String username;
    private String password;
    private boolean isLocked;
    private List<Entry> entries;

    public Diary (String username, String password) {
        validateInputs(username, password);
        this.username = username;
        this.password = password;
        this.isLocked = true;
        this.entries = new ArrayList<>();
    }

    private void validateInputs(String username, String password) {
       if (username == null || username.isEmpty()) {
           throw new IllegalArgumentException("Username cannot be empty");
       }

       if (password == null || password.isEmpty()) {
           throw new IllegalArgumentException("Password cannot be empty");
       }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void unlockDiary(String password) {
        if (password == null || !password.equals(this.password)) {
            return;
        }
        isLocked = false;
    }


    public void lockDiary() {
        isLocked = true;
    }


    public void createEntry(String title, String body) {
        if (isLocked) {
            throw new IllegalStateException("Cannot create entry while diary is locked");
        }
        Entry entry = new Entry(title, body);
        entries.add(entry);
    }

    public Entry findEntryById(int id) {
        if (isLocked) {
            throw new IllegalStateException("Cannot find entry while diary is locked");
        }
        for (Entry entry : entries) {
            if (entry.getIdentificationNumber() == id) {
                return entry;
            }
        }
        return null;
    }

    public void updateEntry(int id, String title, String content) {
        if (isLocked) {
            throw new IllegalStateException("Cannot update entry: diary is locked");
        }
        Entry entry = findEntryById(id);
        if (entry == null) {
            throw new IllegalArgumentException("Entry with ID " + id + " not found");
        }
        entry.setTitle(title);
        entry.setBody(content);
    }

    public void deleteEntry(int id) {
        if (isLocked) {
            throw new IllegalStateException("Cannot delete entry: diary is locked");
        }
        entries.removeIf(entry -> entry.getIdentificationNumber() == id);
    }
}
