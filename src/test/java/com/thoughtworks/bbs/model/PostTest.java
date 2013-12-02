package com.thoughtworks.bbs.model;

/**
 * Created with IntelliJ IDEA.
 * User: st
 * Date: 13-12-2
 * Time: 下午8:15
 * To change this template use File | Settings | File Templates.
 */
public class PostTest {
    private Post post;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String timeFormat;

    @Before
    public void setup(){


        post = new Post();
        post.setAuthorName("st");
        post.setTitle("Introduce to TDD");
        post.setCreateTime(new Date());
        timeFormat = dateFormat.format(post.getCreateTime()) ;

    }

    @Test
    public void createTimeStringShouldHaveValue(){
        assertThat(post.getcreateTimeString(),is(notNullValue()));
    }

    public void createTimeStringShouldBeFormat(){
        assertThat(post.getcreateTimeString(),is(timeFormat));
    }
}
