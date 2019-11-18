//package rest;
//
//import facades.SwappiFacade;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.UriInfo;
//import javax.ws.rs.Produces;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.core.MediaType;
//
///**
// * REST Web Service
// *
// * @author Henrik
// */
//@Path("swappi")
//public class SwappiResource {
//
//    @Context
//    private UriInfo context;
//
//    /**
//     * Creates a new instance of SwappiResource
//     */
//    public SwappiResource() {
//    }
//
//    /**
//     * Retrieves representation of an instance of rest.SwappiResource
//     *
//     * @return an instance of java.lang.String
//     */
//    @Path("all")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getJson() {
//        try {
//          //  SwappiFacade swappi = new SwappiFacade();
////
////            List<String> s = new ArrayList<>();
////            s.add(swappi.getSwappiData(1));
////            s.add(swappi.getSwappiData(2));
////            s.add(swappi.getSwappiData(3));
////            s.add(swappi.getSwappiData(4));            
////            s.add(swappi.getSwappiData(5));            
//            
//            return swappi.getAll().toString();
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//        return null;
//    }
//
//}