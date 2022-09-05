/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.persistence.impl.Redundant;
import edu.eci.arsw.blueprints.persistence.impl.Subsampling;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
    }

    @Test
    public void getBlueprint(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        try{
            Blueprint bp = ibpp.getBlueprint("_authorname_", "_bpname_ ");
            assertEquals(bp.getAuthor(), "_authorname_");
            assertEquals(bp.getName(), "_bpname_ ");
        }catch (BlueprintNotFoundException e) {
            fail("Error in the author or name of the blueprint");
        }
    }

    @Test
    public void getBlueprintByAuthor(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp=new Blueprint("mack", "mypaint",pts0);
        Point[] pts1=new Point[]{new Point(46, 46),new Point(16, 16)};
        Blueprint bp1=new Blueprint("mack", "mypaints",pts1);
        try{
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp1);
            assertEquals(ibpp.getBlueprintsByAuthor("mack").size(), 2);
        } catch (BlueprintPersistenceException | BlueprintNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void redundantFilter(){
        Point[] pts0=new Point[]{new Point(13, 41),new Point(12, 40), new Point(12, 41), new Point(12, 41)};
        Blueprint bp=new Blueprint("mack", "mypaint",pts0);
        Redundant r = new Redundant();
        Blueprint blp = r.filtro(bp);
        assertEquals(blp.getPoints().get(0).getX(), 13);
        assertEquals(blp.getPoints().get(1).getX(), 12);
        assertEquals(blp.getPoints().get(2).getX(), 12);
        assertEquals(blp.getPoints().get(0).getY(), 41);
        assertEquals(blp.getPoints().get(1).getY(), 40);
        assertEquals(blp.getPoints().get(2).getY(), 41);
    }

    @Test
    public void subsampling(){
        Point[] pts0=new Point[]{new Point(13, 41),new Point(12, 40), new Point(12, 41), new Point(12, 41)};
        Blueprint bp=new Blueprint("mack", "mypaint",pts0);
        Subsampling s = new Subsampling();
        Blueprint blp = s.filtro(bp);
        assertEquals(blp.getPoints().get(0).getX(), 13);
        assertEquals(blp.getPoints().get(0).getY(), 41);
        assertEquals(blp.getPoints().get(1).getX(), 12);
        assertEquals(blp.getPoints().get(1).getY(), 41);
    }

    
}
