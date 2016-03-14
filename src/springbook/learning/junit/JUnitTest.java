package springbook.learning.junit;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;

public class JUnitTest {

	static JUnitTest testObject;
	
	@Test
	public void test1() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}
	
	@Test
	public void test2() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}
	
	@Test
	public void test3() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}

}
