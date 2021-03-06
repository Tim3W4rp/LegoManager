package cz.fi.muni.legomanager.services;

import cz.fi.muni.legomanager.entity.*;

import java.util.List;
import java.util.Set;

/**
 * @author Martin Jordán
 */
public interface KitService {
    public Long createKit(Kit kit);
    public Kit findKitById(long id);
    public Long updateKit(Kit kit);
    public void deleteKitById(long id);
    public List<Kit> findAllKits();
    public Set<Kit> getKitsByCategoryId(long categoryId);
    public List<Kit> findSimilarKits(Kit similarKit, int priceRange, int ageLimitRange);
    public Brick findBrickById(long id);
    Kit createRandomKitByRules(int minBrickCountInKit, int maxBrickCountInKit, List<Brick> bricksInKit);
    public void addKitToSet(Kit kit, SetOfKits setOfKits);
    public void removeAllBricksOfThisTypeFromKitById(long kitId, long brickId);
    public void decreaseBrickCountByOne(long kitId, long brickId);
    public void addBrickToKitById(long kitId, long brickId);
    public long getBrickCount(long kitId, long brickId);
    public void setBrickCount(long kitId, long brickId, int amount);
}
