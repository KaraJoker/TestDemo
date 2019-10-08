package test.java.com.highto.framework.web.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.highto.framework.web.page.ListPage;

public class ListPageTest {

	@Test
	public void test() {
		List items = new ArrayList();
		items.add(1);
		items.add(2);
		items.add(3);
		items.add(4);
		items.add(5);

		int pageNum = 1;
		int pageItemsCount = 5;
		int totalItemsCount = 10;

		ListPage page = new ListPage(items, pageNum, pageItemsCount, totalItemsCount);
		assertEquals(2, page.getPageCount());
		assertFalse(page.isHasPreviousPage());
		assertTrue(page.isHasNextPage());

		pageNum = 2;
		page = new ListPage(items, pageNum, pageItemsCount, totalItemsCount);
		assertEquals(2, page.getPageCount());
		assertTrue(page.isHasPreviousPage());
		assertFalse(page.isHasNextPage());
	}

}
