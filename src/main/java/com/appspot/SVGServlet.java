package com.appspot;


//import com.lowagie.text.xml.XMLWorkerHelper;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.data.*;
import org.fit.cssbox.demo.PdfImageRenderer;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eljah32 on 9/26/2017.
 */
public class SVGServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        OsmConnection osm = new OsmConnection(
                "http://api.openstreetmap.org/api/0.6/",
                "BusRouteDataExtractor 1.0", null);

        MapDataDao mapDao = new MapDataDao(osm);
/*
        double LONGITUDE_START = 48.833744;
        double LATITUDE_START = 55.693307;


        double LONGITUDE_STOP = 49.261698;
        double LATITUDE_STOP = 55.897801;

        double currentLong = LONGITUDE_START;
        double currentLat = LATITUDE_START;

        final HashMap<String, Relation> busRoutes = new HashMap<String, Relation>();

        MapDataHandler mapDataHandler = new MapDataHandler() {
            public void handle(BoundingBox boundingBox) {
            }

            public void handle(Node node) {
            }

            public void handle(Way way) {
            }

            public void handle(Relation relation) {
                if (relation.getTags().get("type").equals("route")) {
                    if (relation.getTags().get("route").equals("bus")) {
                        System.out.println(relation.getTags().get("name"));
                        busRoutes.put(relation.getTags().get("name"), relation);
                    }
                }

            }
        };

        OsmLatLon min = null;
        OsmLatLon max = null;
        while (currentLat < LATITUDE_STOP) {

            while (currentLong < LONGITUDE_STOP) {
                //System.out.println(currentLat+", "+currentLong);
                min = new OsmLatLon(currentLat, currentLong);
                max = new OsmLatLon(currentLat + 0.02, currentLong + 0.02);
                BoundingBox kazanBuses = new BoundingBox(min, max);
                try {
                    mapDao.getMap(kazanBuses, mapDataHandler);
                } catch (de.westnordost.osmapi.common.errors.OsmApiReadResponseException e) {
                    System.out.println(e.toString());

                } catch (de.westnordost.osmapi.common.errors.OsmConnectionException e) {
                    try {
                        Thread.sleep(10000);
                        //currentLong = currentLong -0.2;

                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println(e.toString());
                }


                currentLong = currentLong + 0.02;
            }
            currentLong = LONGITUDE_START;
            currentLat = currentLat + 0.02;
        }

        Iterator it = busRoutes.entrySet().iterator();
        while (it.hasNext())

        {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + ((Relation)pair.getValue()).getId());
            it.remove(); // avoids a ConcurrentModificationException
        }
*/

        Relation bus=mapDao.getRelation(1282305);
        //bus.g
        for (RelationMember member: bus.getMembers()) {

            if (member.getType().equals(Element.Type.NODE))
            {
                //System.out.println(member.getRef());
                Node stop=mapDao.getNode(member.getRef());
                System.out.println(stop.getTags().get("name"));System.out.println(stop.getTags().get("name:tt"));System.out.println(stop.getTags().get("name:en"));

            }

            List<Node> routeNodes=new ArrayList<Node>();
            if (member.getType().equals(Element.Type.WAY))
            {
                //System.out.println(member.getRef());
                Way line=mapDao.getWay(member.getRef());
                for (Long id: line.getNodeIds()) {
                    routeNodes.add(mapDao.getNode(id));
                }

            }
            for (Node id: routeNodes) {
                System.out.println(id.getPosition().getLatitude()+" "+id.getPosition().getLongitude());;
            }



        }



        final String FONT = "TimesNewRomanRegular.ttf";
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);

        OutputStream outStream = resp.getOutputStream();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
/*
        Document document = XMLResource.load(new ByteArrayInputStream(sb.toString().getBytes())).getDocument();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(document,"test");
        String outputFile = "test.pdf";
        OutputStream os = new FileOutputStream(outputFile);
        renderer.layout();
        renderer.createPDF(os);
        os.close();

*/
        InputStream is = null;
        try {
            is = new URL("http://localhost:8080/kazan.html").openStream();
        } catch (Exception e) {
            System.out.println("Url problem: " + e.getMessage());
        }
        Tidy tidy = new Tidy();
        tidy.setShowWarnings(false);
        tidy.setXmlTags(false);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);//
        tidy.setMakeClean(true);

        org.w3c.dom.Document xmlDoc = tidy.parseDOM(is, null);

        tidy.pprint(xmlDoc, byteArrayOutputStream);

        System.out.println(byteArrayOutputStream.toString("UTF-8"));

        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        InputStream cis =new URL("http://localhost:8080/css.css").openStream();



        Document document = new Document();
        // step 2
        //Rectangle two = new Rectangle(1700,1400);
        //document.setPageSize(two);
        //document.setMargins(2, 2, 2, 2);

        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, outStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        // step 3
        document.open();
        // step 4



        XMLWorkerHelper.getInstance().parseXHtml(writer, document, byteArrayInputStream,cis, Charset.forName("UTF-8"),  fontImp);
        //XMLWorkerHelper.getInstance().parseXHtml(writer, document, byteArrayInputStream,cis, Charset.forName("UTF-8"));
        //step 5
        document.close();

        System.out.println("PDF Created!");


        //PdfImageRenderer renderer=new PdfImageRenderer();
        //renderer.setMediaType("print");
        //renderer.setWindowSize(new Dimension(1200, 600), false);
        //renderer.setLoadImages(true, true);

        //OutputStream os = null;
        //    os = outStream;

        //try {
        //    renderer.renderURL("http://biketour.tatar", os, PdfImageRenderer.Type.SVG, "A0");
        //} catch (IOException e) {
        //    e.printStackTrace();
        //} catch (SAXException e) {
        //    e.printStackTrace();
        //}
        //try {
        //    os.close();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        resp.setContentType("application/pdf");
    }
}
