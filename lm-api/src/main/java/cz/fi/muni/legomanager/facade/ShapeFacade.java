package cz.fi.muni.legomanager.facade;

import cz.fi.muni.legomanager.dto.ShapeCreateDTO;
import cz.fi.muni.legomanager.dto.ShapeDTO;

import java.util.List;

/**
 * @author Lukáš Dvořák
 */
public interface ShapeFacade {

    /**
     * Create the given shape.
     *
     * @param shapeDTO shape to be created
     * @return created shape id
     */
    Long create(ShapeCreateDTO shapeDTO);

    /**
     * Update the given shape.
     *
     * @param shapeDTO shape to be updated
     */
    void update(ShapeDTO shapeDTO);

    /**
     * Delete the given shape.
     *
     * @param shapeId id of the shape
     */
    void delete(Long shapeId);

    /**
     * Get shape with the given id.
     *
     * @param shapeId id of the shape
     * @return existing shape with given id
     */
    ShapeDTO findById(Long shapeId);

    /**
     * Get shape by name.
     *
     * @param name name of the shape
     * @return existing shape with the given name
     */
    ShapeDTO findByName(String name);

    /**
     * Get all existing shapes.
     *
     * @return list of existing shapes
     */
    List<ShapeDTO> findAll();

}
