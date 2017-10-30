package cz.fi.muni.legomanager.dao;

import cz.fi.muni.legomanager.PersistenceSampleApplicationContext;
import cz.fi.muni.legomanager.entity.Category;
import cz.fi.muni.legomanager.entity.Kit;
import cz.fi.muni.legomanager.entity.SetOfKits;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test of {@link SetOfKitsDao} methods.
 *
 * @author Lukáš Dvořák
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SetOfKitsDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SetOfKitsDao setOfKitsDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private KitDao kitDao;

    @PersistenceContext
    private EntityManager em;

    private SetOfKits buildingsSet;
    private SetOfKits carsSet;

    private Category buildingsCategory;
    private Category carsCategory;
    private Category boysCategory;

    private Kit eiffelTowerKit;
    private Kit statueOfLibertyKit;
    private Kit towerBridgeKit;

    private Kit porscheKit;
    private Kit ferrariKit;
    private Kit footballerKit;

    @BeforeMethod
    public void setUp() {

        buildingsCategory = new Category();
        buildingsCategory.setName("Buildings");
        buildingsCategory.setDescription("For all architecture fans.");
        categoryDao.create(buildingsCategory);

        carsCategory = new Category();
        carsCategory.setName("Cars");
        carsCategory.setDescription("For all motor sport fans.");
        categoryDao.create(carsCategory);

        boysCategory = new Category();
        boysCategory.setName("Boys");
        boysCategory.setDescription("Suitable for boys.");
        categoryDao.create(boysCategory);

        eiffelTowerKit = new Kit();
        eiffelTowerKit.setDescription("France - Paris");
        eiffelTowerKit.setAgeLimit(8);
        eiffelTowerKit.setPrice(159);
        kitDao.create(eiffelTowerKit);

        statueOfLibertyKit = new Kit();
        statueOfLibertyKit.setDescription("USA - New York");
        statueOfLibertyKit.setAgeLimit(8);
        statueOfLibertyKit.setPrice(129);
        kitDao.create(statueOfLibertyKit);

        towerBridgeKit = new Kit();
        towerBridgeKit.setDescription("Great Britain - London");
        towerBridgeKit.setAgeLimit(12);
        towerBridgeKit.setPrice(139);
        kitDao.create(towerBridgeKit);

        porscheKit = new Kit();
        porscheKit.setDescription("Max speed - 280 km/h");
        porscheKit.setAgeLimit(3);
        porscheKit.setPrice(59);
        kitDao.create(porscheKit);

        ferrariKit = new Kit();
        ferrariKit.setDescription("Max speed - 300 km/h");
        ferrariKit.setAgeLimit(5);
        ferrariKit.setPrice(79);
        kitDao.create(ferrariKit);

        footballerKit = new Kit();
        footballerKit.setDescription("Scores from every shot.");
        footballerKit.setAgeLimit(6);
        footballerKit.setPrice(69);
        kitDao.create(footballerKit);

        buildingsSet = new SetOfKits();
        buildingsSet.setDescription("Set of the most famous buildings.");
        buildingsSet.setPrice(new BigDecimal("429"));
        buildingsSet.addCategory(buildingsCategory);
        buildingsSet.addKit(eiffelTowerKit);
        buildingsSet.addKit(statueOfLibertyKit);
        buildingsSet.addKit(towerBridgeKit);
        setOfKitsDao.create(buildingsSet);

        carsSet = new SetOfKits();
        carsSet.setDescription("Set of the fastest cars on the planet.");
        carsSet.setPrice(new BigDecimal("189"));
        carsSet.addCategory(carsCategory);
        carsSet.addCategory(boysCategory);
        carsSet.addKit(porscheKit);
        carsSet.addKit(ferrariKit);
        setOfKitsDao.create(carsSet);

    }

    @Test
    public void testCreate() {

        SetOfKits sportsSet = new SetOfKits();
        sportsSet.setDescription("Set of the sportsmen.");
        sportsSet.setPrice(new BigDecimal("309"));
        sportsSet.addCategory(boysCategory);
        sportsSet.addKit(footballerKit);
        setOfKitsDao.create(sportsSet);

        Long sportsSetId = sportsSet.getId();
        assertNotNull(sportsSetId);

        SetOfKits actual = setOfKitsDao.findById(sportsSetId);
        assertEquals(sportsSet, actual);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNullSet() {
        setOfKitsDao.create(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateAlreadyExists() {
        setOfKitsDao.create(carsSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateSetWithNullDescription() {
        SetOfKits wrongSet = new SetOfKits();
        wrongSet.setDescription(null);
        wrongSet.setPrice(new BigDecimal("189"));
        wrongSet.addCategory(carsCategory);
        wrongSet.addKit(porscheKit);

        setOfKitsDao.create(wrongSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateSetWithNullPrice() {
        SetOfKits wrongSet = new SetOfKits();
        wrongSet.setDescription("Wrong set.");
        wrongSet.setPrice(null);
        wrongSet.addCategory(carsCategory);
        wrongSet.addKit(porscheKit);

        setOfKitsDao.create(wrongSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateSetWithNegativePrice() {
        SetOfKits wrongSet = new SetOfKits();
        wrongSet.setDescription("Wrong set.");
        wrongSet.setPrice(new BigDecimal("-189"));
        wrongSet.addCategory(carsCategory);
        wrongSet.addKit(porscheKit);

        setOfKitsDao.create(wrongSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateSetWithNullCategory() {
        SetOfKits wrongSet = new SetOfKits();
        wrongSet.setDescription("Wrong set.");
        wrongSet.setPrice(new BigDecimal("189"));
        wrongSet.addCategory(null);
        wrongSet.addKit(porscheKit);

        setOfKitsDao.create(wrongSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateSetWithNullKits() {
        SetOfKits wrongSet = new SetOfKits();
        wrongSet.setDescription("Wrong set.");
        wrongSet.setPrice(new BigDecimal("189"));
        wrongSet.addCategory(carsCategory);
        wrongSet.addKit(null);

        setOfKitsDao.create(wrongSet);
    }

    @Test
    public void testUpdate() {
        buildingsSet.setPrice(new BigDecimal("100"));

        setOfKitsDao.update(buildingsSet);

        SetOfKits actual = setOfKitsDao.findById(buildingsSet.getId());

        assertEquals(buildingsSet, actual);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNullSet() {
        setOfKitsDao.update(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSetWithNullDescription() {
        buildingsSet.setDescription(null);

        setOfKitsDao.update(buildingsSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSetWithNullPrice() {
        buildingsSet.setPrice(null);

        setOfKitsDao.update(buildingsSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSetWithNegativePrice() {
        buildingsSet.setPrice(new BigDecimal("-100"));

        setOfKitsDao.update(buildingsSet);
    }

    @Test
    public void testDelete() {
        setOfKitsDao.delete(buildingsSet);

        List<SetOfKits> existingSets = setOfKitsDao.findAll();
        List<SetOfKits> expectedSets = new ArrayList<>();
        expectedSets.add(carsSet);

        assertEquals(existingSets.size(), 1);
        assertEquals(expectedSets, existingSets);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNotExist() {
        SetOfKits wrongSet = new SetOfKits();
        wrongSet.setDescription("Wrong set.");
        wrongSet.setPrice(new BigDecimal("500"));
        wrongSet.addCategory(buildingsCategory);
        wrongSet.addKit(eiffelTowerKit);

        setOfKitsDao.delete(wrongSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteAlreadyRemoved() {
        setOfKitsDao.delete(buildingsSet);
        em.flush();

        setOfKitsDao.delete(buildingsSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNullSet() {
        setOfKitsDao.delete(null);
    }

    @Test
    public void testFindSetById() {
        SetOfKits foundSet = setOfKitsDao.findById(carsSet.getId());
        assertEquals(foundSet, carsSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindSetByIdWithNonExistingId() {
        setOfKitsDao.findById(20L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindSetByIdNull() {
        setOfKitsDao.findById(null);
    }

    @Test
    public void testFindAll() {
        List<SetOfKits> foundSets = setOfKitsDao.findAll();
        assertEquals(foundSets.size(), 2);
    }

}
