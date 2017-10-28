package cz.fi.muni.legomanager.dao;

import cz.fi.muni.legomanager.entity.Brick;

import java.util.List;

/**
 * CRUD operations for entity Brick.
 *
 * @author Lukáš Dvořák
 */
public interface BrickDao {

    /**
     * Creates a new brick in DB.
     *
     * @param brick brick to be added to DB
     */
    public void create(Brick brick);

    /**
     * Updates an already existing brick in DB.
     *
     * @param brick brick to be updated in DB
     */
    public void update(Brick brick);

    /**
     * Deletes a brick from DB.
     *
     * @param brick brick to be deleted from DB
     */
    public void delete(Brick brick);

    /**
     * Returns brick with corresponding id.
     *
     * @param id id of the brick
     * @return brick with corresponding id
     */
    public Brick findById(Long id);

    /**
     * Returns list of all existing bricks in DB.
     *
     * @return list of all existing bricks
     */
    public List<Brick> findAll();

}
