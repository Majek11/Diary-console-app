package test;

import app.Diary;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DiaryTest {
    private static  final String TEST_USERNAME = "user1234";
    private static  final String TEST_PASSWORD = "password1234";
    private Diary diary;

    @BeforeEach
    public void setUp() {
        diary = new Diary(TEST_USERNAME, TEST_PASSWORD);
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


}
