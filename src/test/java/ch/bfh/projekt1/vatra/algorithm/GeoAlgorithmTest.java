package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.Duration;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * Try to evaluate every possible NullpointerException and test it.
 * <p>
 * Created by dave on 21.01.16.
 */
public class GeoAlgorithmTest {

    @Test
    public void getShortestDelta() throws Exception {
        GeoAlgorithm geoAlgorithm = new GeoAlgorithm();
        DistanceMatrixRow[] distanceMatrixRow = new DistanceMatrixRow[0];
        String[] originAddresses = new String[0];
        String[] destinationAddresses = new String[0];
        Request lastRequest = new Request("", new App(), "");

        Optional<Long> shortestDelta = geoAlgorithm.getShortestDelta(lastRequest, new DistanceMatrix(originAddresses, destinationAddresses, distanceMatrixRow));

        Assert.assertFalse(shortestDelta.isPresent());

        distanceMatrixRow = new DistanceMatrixRow[1];
        originAddresses = new String[1];
        destinationAddresses = new String[1];

        shortestDelta = geoAlgorithm.getShortestDelta(lastRequest, new DistanceMatrix(originAddresses, destinationAddresses, distanceMatrixRow));
        Assert.assertFalse(shortestDelta.isPresent());

        distanceMatrixRow = new DistanceMatrixRow[1];
        DistanceMatrixRow distanceMatrixRow1 = new DistanceMatrixRow();
        distanceMatrixRow[0] = distanceMatrixRow1;
        originAddresses = new String[1];
        destinationAddresses = new String[1];

        shortestDelta = geoAlgorithm.getShortestDelta(lastRequest, new DistanceMatrix(originAddresses, destinationAddresses, distanceMatrixRow));

        Assert.assertFalse(shortestDelta.isPresent());

        distanceMatrixRow1.elements = new DistanceMatrixElement[0];

        shortestDelta = geoAlgorithm.getShortestDelta(lastRequest, new DistanceMatrix(originAddresses, destinationAddresses, distanceMatrixRow));

        Assert.assertFalse(shortestDelta.isPresent());

        distanceMatrixRow1.elements = new DistanceMatrixElement[1];

        shortestDelta = geoAlgorithm.getShortestDelta(lastRequest, new DistanceMatrix(originAddresses, destinationAddresses, distanceMatrixRow));

        Assert.assertFalse(shortestDelta.isPresent());

        DistanceMatrixElement distanceMatrixElement = new DistanceMatrixElement();
        distanceMatrixElement.duration = new Duration();
        int one_hour = 60 * 60;
        distanceMatrixElement.duration.inSeconds = one_hour; //1h
        distanceMatrixRow1.elements[0] = distanceMatrixElement;

        shortestDelta = geoAlgorithm.getShortestDelta(lastRequest, new DistanceMatrix(originAddresses, destinationAddresses, distanceMatrixRow));

        Assert.assertTrue(shortestDelta.isPresent());
        Assert.assertEquals(shortestDelta.get(), Long.valueOf(one_hour));
    }
}