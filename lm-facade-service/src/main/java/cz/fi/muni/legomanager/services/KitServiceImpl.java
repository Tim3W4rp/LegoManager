package cz.fi.muni.legomanager.services;

import cz.fi.muni.legomanager.dao.BrickDao;
import cz.fi.muni.legomanager.dao.CategoryDao;
import cz.fi.muni.legomanager.dao.KitDao;
import cz.fi.muni.legomanager.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Martin Jordán
 */
@Service
public class KitServiceImpl implements KitService {

    @Autowired
    private KitDao kitDao;

    @Autowired
    private BrickDao brickDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private KitBrickService kitBrickService;

    @Override
    public Long createKit(Kit kit) {
        if (kit == null) {
            throw new IllegalArgumentException("Kit cannot be null.");
        }

        kitDao.create(kit);
        return kit.getId();
    }

    @Override
    public Kit findKitById(long id) {
        Kit kit = kitDao.findById(id);
        if (kit == null) {
            throw new RuntimeException("Kit with such ID does not exist");
        }

        return kit;
    }

    @Override
    public Long updateKit(Kit kit) {
        if (kit == null) {
            throw new IllegalArgumentException("Kit cannot be null.");
        }

        kitDao.update(kit);
        return kit.getId();
    }

    @Override
    public void deleteKitById(long id) {
        Kit kit = kitDao.findById(id);
        if (kit == null) {
            throw new RuntimeException("Deleting non-existant kit");
        }

        kitDao.delete(kit);
    }

    @Override
    public List<Kit> findAllKits(){
        return kitDao.findAll();
    }

    @Override
    public Set<Kit> getKitsByCategoryId(long categoryId) {
        Category category = categoryDao.findById(categoryId);
        return category.getKits();
    }

    @Override
    public Brick findBrickById(long id) {
        Brick brick = brickDao.findById(id);
        if (brick == null) {
            throw new RuntimeException("Such brick does not exist");
        }

        return brick;
    }

    @Override
    public List<Kit> findSimilarKits(Kit similarKit, int priceRange, int ageLimitRange, Category category) {
        if (similarKit == null) {
            throw new RuntimeException("Kit is null");
        }

        List<Kit> allKits = kitDao.findAll();
        if (allKits.isEmpty()) {
            throw new RuntimeException("There are no kits to compare this kit to");
        }

        List<Kit> similarKits = new ArrayList<>();
        Integer lowPrice = similarKit.getPrice() - priceRange;
        Integer highPrice = similarKit.getPrice() + priceRange;
        Integer lowAge = similarKit.getAgeLimit() - ageLimitRange;
        Integer highAge = similarKit.getAgeLimit() + ageLimitRange;

        for (Kit kit : allKits) {
            if (((kit.getPrice() >= lowPrice && kit.getPrice() <= highPrice) &&
                    (kit.getAgeLimit() >= lowAge && kit.getAgeLimit() <= highAge) &&
                    kit.getCategory().equals(category)) && !similarKits.contains(kit)) {
                similarKits.add(kit);
            }
        }

        return similarKits;
    }

    @Override
    public void addKitToSet(Kit kit, SetOfKits setOfKits) {
        setOfKits.addKit(kit);
    }


    // Important note: BrickCounts contains counts and bricks that must appear in kit (count>0) and those that can appear(count=0)
    // Note: check M:N if it works
    @Override
    public Long createRandomKitByRules(Long minBrickCount, Long maxBrickCount, Map<Brick, Long> bricksCounts) {
        Kit randomKit = new Kit();
        Long piecesTotal = countPieces(bricksCounts);
        Map<Brick, Long> canBeBricksCounts = getPossibleBrickCounts(bricksCounts);

        int bricksMin = minBrickCount.intValue() - piecesTotal.intValue();
        Long bricksDifference = (maxBrickCount - minBrickCount);
        int countOfBricksToAdd = bricksMin + getRandomNumberUpTo(bricksDifference.intValue());

        Brick[] arrayBricks = (Brick[])canBeBricksCounts.keySet().toArray();

        // Add random number of bricks
        while(countOfBricksToAdd > 0) {
            Long lastValue;
            int brickIndex = getRandomNumberUpTo(canBeBricksCounts.size());
            int addCount = getRandomNumberUpTo(countOfBricksToAdd);

            Brick selectedBrick = arrayBricks[brickIndex];
            lastValue = bricksCounts.get(selectedBrick);
            bricksCounts.put(selectedBrick, Long.valueOf(addCount) + lastValue);

            countOfBricksToAdd -= addCount;
        }

        for (Map.Entry<Brick, Long> entry : bricksCounts.entrySet()) {
            if (entry.getValue() > 0L) {
                kitBrickService.addBrickToKit(randomKit, entry.getKey());
                KitBrick kitBrick = new KitBrick();
                kitBrick.setBrick(entry.getKey());
                kitBrick.setCount(entry.getValue());

            }
        }

        Kit createdKit = kitDao.create(randomKit);
        return createdKit.getId();

    }

    private Long countPieces(Map<Brick, Long> mappedBrickCount) {
        Long count = 0L;
        for (Map.Entry<Brick, Long> entry : mappedBrickCount.entrySet()) {
            count += entry.getValue();
        }
        return count;
    }

    private Map<Brick, Long> getPossibleBrickCounts(Map<Brick, Long> mappedBrickCount) {
        Map<Brick, Long> possibleBrickCounts = new LinkedHashMap<>();
        for (Map.Entry<Brick, Long> entry : mappedBrickCount.entrySet()) {
            if (entry.getValue() == 0L) {
                possibleBrickCounts.put(entry.getKey(), entry.getValue());
            }
        }
        return possibleBrickCounts;
    }

    private int getRandomNumberUpTo(int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(max);
    }

    @Override
    public void removeAllBricksOfThisTypeFromKitById(long kitId, long brickId) {
        Kit kit = kitDao.findById(kitId);
        Brick brick = brickDao.findById(brickId);
        kitBrickService.removeAllBricksOfThisType(kit, brick);
    }

    @Override
    public void decreaseBrickCountByOne(long kitId, long brickId){
        Kit kit = kitDao.findById(kitId);
        Brick brick = brickDao.findById(brickId);
        kitBrickService.decreaseBrickCountByOne(kit, brick);
    }

    @Override
    public void addBrickToKitById(long kitId, long brickId) {
        Kit kit = kitDao.findById(kitId);
        Brick brick = brickDao.findById(brickId);
        kitBrickService.addBrickToKit(kit, brick);
    }

    @Override
    public long getBrickCount(long kitId, long brickId) {
        Kit kit = kitDao.findById(kitId);
        Brick brick = brickDao.findById(brickId);
        return kitBrickService.getBrickCount(kit, brick);
    }
}
