package data.loader;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import data.loader.domain.Data;
import data.loader.repository.DataRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JdbcCanaryInjectorTests {

	@Autowired
	DataRepository dataRepository;

	@Test
	public void loadData() {
		final List<Data> data = (ArrayList<Data>) this.dataRepository.findAll();
		assertEquals("Did not get all data", 3, data.size());
	}

	@Before
	public void setUp() throws Exception {
		this.dataRepository.deleteAll();
		final Data FirstRow = new Data("FirstRow");
		final Data SecondRow = new Data("SecondRow");
		final Data camelUp = new Data("ThirdRow");
		this.dataRepository.save(FirstRow);
		this.dataRepository.save(SecondRow);
		this.dataRepository.save(camelUp);
	}

	@Test
	public void testCRUD() throws Exception {
		// Create a new game
		final Data testRow = new Data("testRow");
		this.dataRepository.save(testRow);

		// Assert it was created
		final List<Data> foundTestRow = this.dataRepository.findByStr(testRow.getStr());
		assertEquals("Did not find testRow", testRow.getStr(), foundTestRow.get(0).getStr());

		// Edit it's str
		final String newStr = "testRow2";
		foundTestRow.get(0).setStr(newStr);

		// Save to the database (note that we can save not just single Entities, but collections of them as well)
		this.dataRepository.save(foundTestRow);

		// Assert it updated
		final List<Data> updatedGame = this.dataRepository.findByStr(newStr);
		assertEquals("Did not update str", newStr, updatedGame.get(0).getStr());

		// Delete game
		this.dataRepository.delete(updatedGame);

		// Assert not found
		final List<Data> empty = this.dataRepository.findByStr(testRow.getStr());
		assertEquals("Should have returned no games", 0, empty.size());
	}

	@Test
	public void testFindData() throws Exception {
		final List<Data> camelUpList = this.dataRepository.findByStr("ThirdRow");
		assertEquals("Found wrong number of ThirdRows", 1, camelUpList.size());

	}
}
