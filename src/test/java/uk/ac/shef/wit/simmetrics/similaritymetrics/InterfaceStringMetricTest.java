package uk.ac.shef.wit.simmetrics.similaritymetrics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public abstract class InterfaceStringMetricTest {

	public class T {
		protected final float similarity;
		protected final String string1;
		protected final String string2;

		public T(float similarity, String string1, String string2) {
			this.string1 = string1;
			this.string2 = string2;
			this.similarity = similarity;
		}

	}

	private static final float DEFAULT_DELTA = 0.0001f;
	private float delta;

	protected InterfaceStringMetric metric;

	public abstract InterfaceStringMetric getMetric();

	public abstract T[] getTests();

	@Before
	public void setUp() throws Exception {
		metric = getMetric();
		delta = getDelta();
	}

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	@Test
	public void testGetSimilarity() {
		for (T t : getTests()) {
			String message = String.format("\"%s\" vs \"%s\"", t.string1, t.string2);
			float actuall = metric.getSimilarity(t.string1, t.string2);
			assertEquals(message, t.similarity, actuall, delta);
		}
	}

	@Test
	public void testGetSimilarityExplained() {
		assertNull(metric.getSimilarityExplained(null, null));
	}
	
	@Test
	public void testGetShortDescriptionString() {
		assertEquals(metric.getClass().getSimpleName(), metric.getShortDescriptionString());
	}

}