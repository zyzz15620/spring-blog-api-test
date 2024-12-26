package common;

import static common.EnvConfig.getDotEnv;

public class ConstantUtils {
    public static final String BASE_URL = "http://" + getDotEnv().get("host");
    public static final String PORT = getDotEnv().get("port");

    //Post Resource Paths
    public static final String GET_POSTS_PATH = "/api/posts";
    public static final String GET_POST_WITH_ID_PATH = GET_POSTS_PATH + "/{id}";
    public static final String GET_POST_WITH_CATEGORY_PATH = GET_POSTS_PATH + "/category/{id}";
    public static final String SEARCH_POST_PATH = GET_POSTS_PATH + "/search";
    public static final String CREATE_POST_PATH = GET_POSTS_PATH;
    public static final String UPDATE_POST_PATH = GET_POST_WITH_ID_PATH;
    public static final String DELETE_POST_PATH = GET_POST_WITH_ID_PATH;

    //Category Comment Paths
    public static final String GET_COMMENTS_PATH = GET_POST_WITH_ID_PATH + "/comments";
    public static final String GET_COMMENT_WITH_ID_PATH = GET_COMMENTS_PATH + "/{id}";
    public static final String CREATE_COMMENT_PATH = GET_COMMENTS_PATH;
    public static final String UPDATE_COMMENT_PATH = GET_COMMENT_WITH_ID_PATH;
    public static final String DELETE_COMMENT_PATH = GET_COMMENT_WITH_ID_PATH;

    //Category Resource Paths
    public static final String GET_CATEGORIES_PATH = "/api/categories";
    public static final String GET_CATEGORY_WITH_ID_PATH = GET_CATEGORIES_PATH + "/{id}";
    public static final String CREATE_CATEGORY_PATH = GET_CATEGORIES_PATH;
    public static final String UPDATE_CATEGORY_PATH = GET_CATEGORY_WITH_ID_PATH;
    public static final String DELETE_CATEGORY_PATH = GET_CATEGORY_WITH_ID_PATH;

    //Account Resource Paths
    public static final String CREATE_ACCOUNT_PATH = "/api/auth/register";
    public static final String LOGIN_ACCOUNT_PATH = "/api/auth/login";

}
