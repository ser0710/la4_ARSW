package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String a[]) throws BlueprintNotFoundException, BlueprintPersistenceException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
        Point[] pts0=new Point[]{new Point(13, 41),new Point(12, 40), new Point(12, 41), new Point(12, 41)};
        Blueprint bp=new Blueprint("mack", "mypaint",pts0);
//        Point[] pts1=new Point[]{new Point(46, 46),new Point(16, 16)};
//        Blueprint bp1=new Blueprint("mack", "mypaints",pts1);
        bps.addNewBlueprint(bp);
//        bps.addNewBlueprint(bp1);
//
//        Blueprint bp2 = bps.getBlueprint("mack", "mypaint");
//
//
//        System.out.println(bp2.getPoints().get(0).getX());
//        System.out.println(bps.getBlueprintsByAuthor("mack"));
//        Blueprint bpp = bps.redundantFilter(bp);
//        System.out.println(bpp.getPoints().get(0).getX());
//        System.out.println(bpp.getPoints().get(0).getY());
//        System.out.println(bpp.getPoints().get(1).getX());
//        System.out.println(bpp.getPoints().get(1).getY());
//        System.out.println(bpp.getPoints().size());
        Blueprint bpp = bps.redundantFilter(bp);
        System.out.println(bpp.getPoints().size());
        System.out.println(bpp.getPoints().get(0).getX());
        System.out.println(bpp.getPoints().get(0).getY());
        System.out.println(bpp.getPoints().get(1).getX());
        System.out.println(bpp.getPoints().get(1).getY());
        System.out.println(bpp.getPoints().get(2).getX());
        System.out.println(bpp.getPoints().get(2).getY());



    }
}
