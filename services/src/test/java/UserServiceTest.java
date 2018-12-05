import net.tsystems.entitydao.UserDAO;
import net.tsystems.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserDAO userDao;
    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isUniqueUsernameDoneRight() {
        when(userDao.checkUniqueUsername(any(String.class), any(Integer.class))).thenReturn(true);

        String username = "newUser";

        boolean expectedResult = true;
        boolean result = userService.isUniqueUsername(username, 1);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void isUniqueUsernameNotUnique() {
        when(userDao.checkUniqueUsername(any(String.class), any(Integer.class))).thenReturn(false);

        String username = "newUser2";

        boolean expectedResult = false;
        boolean result = userService.isUniqueUsername(username, 1);

        Assert.assertEquals(expectedResult, result);
    }
}
