/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package org.simmetrics.simplifiers;

import java.util.concurrent.ExecutionException;

import org.simmetrics.metrics.SimplifyingSimplifier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CachingSimplifier implements SimplifyingSimplifier {

	private static final int CACHE_SIZE = 2;

	private Simplifier simplifier;

	private LoadingCache<String, String> arrayCache = CacheBuilder.newBuilder()
			.initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE)
			.build(new CacheLoader<String, String>() {

				@Override
				public String load(String key) throws Exception {
					return getSimplifier().simplify(key);
				}

			});

	public CachingSimplifier() {
		// Empty constructor
	}

	public CachingSimplifier(Simplifier simplifier) {
		this.simplifier = simplifier;
	}

	public Simplifier getSimplifier() {
		return simplifier;
	}

	@Override
	public void setSimplifier(Simplifier simplifier) {
		this.simplifier = simplifier;
	}

	public String simplify(String input) {
		try {
			return arrayCache.get(input);
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String toString() {
		return "CachingSimplifier [" + simplifier + "]";
	}

}