package org.opentrafficsim.demo.ntm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.opentrafficsim.core.unit.FrequencyUnit;
import org.opentrafficsim.core.unit.LengthUnit;
import org.opentrafficsim.demo.ntm.Node.TrafficBehaviourType;

/**
 * <p>
 * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights
 * reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version 27 Jan 2015 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://Hansvanlint.weblog.tudelft.nl">Hans van Lint</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @author <a href="http://www.citg.tudelft.nl">Guus Tamminga</a>
 * @author <a href="http://www.citg.tudelft.nl">Yufei Yuan</a>
 */
public class CsvFileWriter
{

    public static void writeParametersNTM(NTMModel model, String pathAndFile) throws IOException
    {
        // temporary: make a file with NTM parameters per area
        BufferedWriter parametersNTMOut = null;
        String textOut;
        File fileParametersNTM = new File(pathAndFile);
        parametersNTMOut = WriteOutput.createWriter(fileParametersNTM);
        for (Node node : model.getAreaGraph().vertexSet())
        {
            if (node.getBehaviourType() == TrafficBehaviourType.NTM)
            {
                BoundedNode bNode = (BoundedNode) node;
                CellBehaviourNTM nodeBehaviour = (CellBehaviourNTM) bNode.getCellBehaviour();
                textOut = bNode.getArea().getCentroidNr();
                textOut += ", ";
                textOut += String.format("%.1f", nodeBehaviour.getParametersNTM().getAccCritical().get(0));
                textOut += ", ";
                textOut += String.format("%.1f", nodeBehaviour.getParametersNTM().getAccCritical().get(1));
                textOut += ", ";
                textOut += String.format("%.1f", nodeBehaviour.getParametersNTM().getAccCritical().get(2));
                textOut += ", ";
                textOut +=
                        String.format("%.1f", (nodeBehaviour.getMaxCapacityNTMArea().getInUnit(FrequencyUnit.PER_HOUR))
                                / nodeBehaviour.getParametersNTM().getRoadLength().getInUnit(LengthUnit.KILOMETER));
                parametersNTMOut.write(textOut + " \n");
            }
        }
        parametersNTMOut.close();
    }

    // temporary: end

    public static void writeCapresNTM(NTMModel model, String pathAndFile, Double factor) throws IOException
    {
        BufferedWriter capResFileWriter = null;
        File filecapRestraintsAreas = new File(pathAndFile);
        capResFileWriter = WriteOutput.createWriter(filecapRestraintsAreas);
        boolean header = true;
        for (Node origin : model.getAreaGraph().vertexSet())
        {
            if (origin.getBehaviourType() == TrafficBehaviourType.NTM
                    | origin.getBehaviourType() == TrafficBehaviourType.CORDON)
            {
                String textOutCapRes = origin.getId();
                String textHeader = "Capacity";
                for (Node destination : model.getAreaGraph().vertexSet())
                {
                    if (destination.getBehaviourType() == TrafficBehaviourType.NTM
                            | destination.getBehaviourType() == TrafficBehaviourType.CORDON)
                    {
                        if (header)
                        {
                            textHeader += ", ";
                            textHeader += destination.getId();
                        }
                        double capacity = 999999;
                        if (origin.getId() != destination.getId())
                        {
                            if (model.getAreaGraph().getEdge(origin, destination) != null)
                            {
                                if (factor == 0.0)
                                {
                                    capacity =
                                            model.getAreaGraph().getEdge(origin, destination).getLink()
                                                    .getCorridorCapacity().getInUnit(FrequencyUnit.PER_HOUR);
                                }
                                else
                                {
                                    capacity = factor;
                                }

                            }
                        }
                        textOutCapRes += ", ";
                        textOutCapRes += String.format("%.1f", capacity);
                    }
                }
                if (header)
                {
                    capResFileWriter.write(textHeader + " \n");
                    header = false;
                }
                capResFileWriter.write(textOutCapRes + " \n");
            }
        }
        capResFileWriter.close();
        // temporary: end
        // !!!!!!!!!!!!!!!!!!
    }

}
