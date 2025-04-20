package test;

import app.Diary;
import app.Entry;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DiaryTest {
    private static  final String TEST_USERNAME = "user1234";
    private static  final String TEST_PASSWORD = "password1234";
    private static final String TEST_TITLE = "title1234";
    private static final String TEST_DESCRIPTION = "description1234";
    private static final String WRONG_PASSWORD = "wrong password1234";

    private Diary diary;

    @BeforeEach
    public void setUp() {
        diary = new Diary(TEST_USERNAME, TEST_PASSWORD);
       // Entry.resetIdCounter();
    }

    // CREATION_TEST
    @Test
    public void testCreateDiaryWithValidInputs() {
        assertEquals(TEST_USERNAME, diary.getUsername());
        assertEquals(TEST_PASSWORD, diary.getPassword());
        assertTrue(diary.isLocked());
    }

    @Test
    public void testCreateDiaryWithNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Diary(null, TEST_PASSWORD));
    }

    @Test
    public void testCreateDiaryWithNullPassword() {
        assertThrows(IllegalArgumentException.class, () -> new Diary(TEST_USERNAME, null));
    }

    @Test
    public void testCreateDiaryWithEmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Diary("", TEST_PASSWORD));
    }

    @Test
    public void testCreateDiaryWithEmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> new Diary(TEST_USERNAME, ""));
    }

    // UNLOCKING TEST
    @Test
    public void testUnlockDiaryWithCorrectPassword() {
        assertTrue(diary.isLocked());
        diary.unlockDiary(TEST_PASSWORD);
        assertFalse(diary.isLocked());
    }

    @Test
    public void testUnlockDiaryWithIncorrectPassword() {
        assertTrue(diary.isLocked());
        diary.unlockDiary(WRONG_PASSWORD);
        assertTrue(diary.isLocked());
    }

    @Test
    public void testLockDiary() {
        assertTrue(diary.isLocked());
        diary.unlockDiary(TEST_PASSWORD);
        assertFalse(diary.isLocked());
        diary.lockDiary();
        assertTrue(diary.isLocked());
    }

    @Test
    public void testCreateEntryWhenUnlocked() {
        diary.unlockDiary(TEST_PASSWORD);
        assertFalse(diary.isLocked(), "Diary must be unlocked to create entry");
        diary.createEntry(TEST_TITLE, TEST_DESCRIPTION);
        Entry entry = diary.findEntryById(1);
        assertNotNull(entry, "Entry should exist");
        assertEquals(TEST_TITLE, entry.getTitle());
        assertEquals(TEST_DESCRIPTION, entry.getBody());
    }

    @Test
    public void testCreateEntryWhenLocked() {
        assertTrue(diary.isLocked(), "Diary should be locked");
        assertThrows(IllegalStateException.class, () -> {
            diary.createEntry("My Entry", "Content");
        }, "Should throw exception when creating entry while locked");
    }

    @Test
    public void testCreateEntryWithNullTitle() {
        diary.unlockDiary(TEST_PASSWORD);
        assertThrows(IllegalArgumentException.class, () -> {
            diary.createEntry(null, "Content");
        }, "Should throw exception for null title");
    }

    @Test
    public void testFindEntryByIdWhenUnlocked() {
        diary.unlockDiary(TEST_PASSWORD);
        diary.createEntry(TEST_TITLE, TEST_DESCRIPTION);
        Entry entry = diary.findEntryById(1);
        assertNotNull(entry, "Entry should be found");
        assertEquals(TEST_TITLE, entry.getTitle());
        assertEquals(TEST_DESCRIPTION, entry.getBody());

        Entry nonexistent = diary.findEntryById(999);
        assertNull(nonexistent, "Nonexistent entry should return null");
    }

    @Test
    public void testFindEntryByIdWhenLocked() {
        diary.unlockDiary(TEST_PASSWORD);
        diary.createEntry(TEST_TITLE, TEST_DESCRIPTION);
        diary.lockDiary();
        assertThrows(IllegalStateException.class, () -> {
            diary.findEntryById(1);
        }, "Should throw exception when finding entry while locked");
    }

    @Test
    public void testUpdateEntryWhenUnlocked() {
        diary.unlockDiary(TEST_PASSWORD);
        diary.createEntry(TEST_TITLE, TEST_DESCRIPTION);
        diary.updateEntry(1, "New Title", "New Content");
        Entry updatedEntry = diary.findEntryById(1);
        assertNotNull(updatedEntry, "Updated entry should exist");
        assertEquals("New Title", updatedEntry.getTitle());
        assertEquals("New Content", updatedEntry.getBody());
    }

    @Test
    public void testUpdateEntryWhenLocked() {
        diary.unlockDiary(TEST_PASSWORD);
        diary.createEntry(TEST_TITLE, TEST_DESCRIPTION);
        diary.lockDiary();
        assertThrows(IllegalStateException.class, () -> {
            diary.updateEntry(1, "New Title", "New Content");
        }, "Should throw exception when updating entry while locked");
    }

    @Test
    public void testUpdateEntryWithNonexistentId() {
        diary.unlockDiary(TEST_PASSWORD);
        assertThrows(IllegalArgumentException.class, () -> {
            diary.updateEntry(999, "New Title", "New Content");
        }, "Should throw exception for nonexistent entry ID");
    }

    @Test
    public void testDeleteEntryWhenUnlocked() {
        diary.unlockDiary(TEST_PASSWORD);
        diary.createEntry(TEST_TITLE, TEST_DESCRIPTION);
        diary.deleteEntry(1);
        Entry deletedEntry = diary.findEntryById(1);
        assertNull(deletedEntry, "Entry should be deleted");
        diary.deleteEntry(999);
    }

    @Test
    public void testDeleteEntryWhenLocked() {
        diary.unlockDiary(TEST_PASSWORD);
        diary.createEntry(TEST_TITLE, TEST_DESCRIPTION);
        diary.lockDiary();
        assertThrows(IllegalStateException.class, () -> {
            diary.deleteEntry(1);
        }, "Should throw exception when deleting entry while locked");

        diary.unlockDiary(TEST_PASSWORD);
        Entry entry = diary.findEntryById(1);
        assertNotNull(entry, "Entry should still exist");
    }

}
