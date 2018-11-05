import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import utils.*;

public class GitApi {

    private static GitUser oneUser;
    private static Repo oneRepo;
    private static Response response;
    private static List<GitUser> gitUsers, gitFollowers;
    private static List<Repo> gitRepos, gitSubscriptions;

    @BeforeAll
    public static void setUp(){
        Utils.setBaseURI("https://api.github.com");
        Utils.setBasePath("/users");
    }

    @BeforeEach
    public void setGitUser(){
        response = get();
        gitUsers = Arrays.asList(response.getBody().as(GitUser[].class));
        oneUser = gitUsers.get(2);
    }

    @Test
    public void findGitUserName(){
        System.out.println("GIT USER IS: "+oneUser.getLogin());
    }

    @Test
    public void verifyGitUserURL(){
        assertEquals(RestAssured.baseURI+RestAssured.basePath+"/"+oneUser.getLogin(), oneUser.getUrl());
    }

    @Test
    public void verifyEndpointResultSetLength(){
//      get("/users").then().assertThat().body(matchesJsonSchemaInClasspath("gitUser.json"));
        assertEquals(30,gitUsers.size());
    }

    @Test
    public void verifyGitFollowersResultSetLength(){
        Response followersResponse = get(oneUser.getFollowers_url());
        List<GitUser> gitUserFollowers = Arrays.asList(followersResponse.getBody().as(GitUser[].class));
        assertEquals(30,gitUserFollowers.size());
    }

    @Test
    public void verifyGitUserReposURL(){
        assertEquals(oneUser.getRepos_url(),RestAssured.baseURI+RestAssured.basePath+"/"+oneUser.getLogin()+"/repos");
    }

    @Test
    public void verifyGitHubRepos(){
        response = get(oneUser.getRepos_url());
        gitRepos = Arrays.asList(response.getBody().as(Repo[].class));
        System.out.println("GIT REPOS FOR "+oneUser.getLogin()+" are "+gitRepos.size());
    }

    @Test
    public void findGitReposnameForAboveUser() {
        response = get(oneUser.getRepos_url());
        gitRepos = Arrays.asList(response.getBody().as(Repo[].class));
        System.out.println("GIT REPOS FOR "+oneUser.getLogin());
        for (Repo repos : gitRepos) {
            System.out.println(repos.getName());
        }
    }

    @Test
    public void findNoOfFollowers(){
        response = get(oneUser.getFollowers_url());
        gitFollowers = Arrays.asList(response.getBody().as(GitUser[].class));
        assertEquals(30, gitFollowers.size());
    }

    @Test
    public void findNoOfSubscriptions(){
        response = get(oneUser.getSubscriptions_url());
        gitSubscriptions = Arrays.asList(response.getBody().as(Repo[].class));
        System.out.println("GIT SUBSCRIPTIONS FOR USER "+oneUser.getLogin()+" ARE "+gitSubscriptions.size());
    }
}
