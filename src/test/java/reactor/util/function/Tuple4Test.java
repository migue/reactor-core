/*
 * Copyright (c) 2011-2017 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package reactor.util.function;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Tuple4Test {

	private Tuple4<Integer, Integer, Integer, Integer> empty = new Tuple4<>(null, null, null, null);
	private Tuple4<Integer, Integer, Integer, Integer> full = new Tuple4<>(1, 2, 3, 4);

	@Test
	public void sparseToString() {
		assertThat(new Tuple4<>(null, 2, 3, 4))
				.hasToString("[,2,3,4]");

		assertThat(new Tuple4<>(1, null, 3, 4))
				.hasToString("[1,,3,4]");

		assertThat(new Tuple4<>(1, 2, 3,null))
				.hasToString("[1,2,3,]");
	}

	@Test
	public void nullsCountedInSize() {
		assertThat(empty.size()).isEqualTo(4);
		assertThat(empty).hasSize(4);
	}

	@Test
	public void getNegativeIndex() {
		assertThat(full.get(-1)).isNull();
	}

	@Test
	public void getTooLargeIndex() {
		assertThat(full.get(10)).isNull();
	}

	@Test
	public void getAllValuesCorrespondToArray() {
		Object[] array = full.toArray();

		for (int i = 0; i < array.length; i++) {
			assertThat(full.get(i)).as("element " + i).isEqualTo(array[i]);
		}
	}

	@Test
	public void sparseIsNotSameAsSmaller() {
		Tuple4<Integer, Integer, Integer, Integer> sparseLeft = new Tuple4<>(null, 1, 2, 3);
		Tuple4<Integer, Integer, Integer, Integer> sparseRight = new Tuple4<>(1, 2, 3, null);
		Tuple3<Integer, Integer, Integer> smaller = new Tuple3<>(1, 2, 3);

		assertThat(sparseLeft.hashCode())
				.isNotEqualTo(sparseRight.hashCode())
				.isNotEqualTo(smaller.hashCode());

		assertThat(sparseLeft)
				.isNotEqualTo(sparseRight)
				.isNotEqualTo(smaller);
	}

	@Test
	public void equalityOfSameReference() {
		assertThat(full).isEqualTo(full);
	}

	@Test
	public void equalityOfNullOrWrongClass() {
		assertThat(full).isNotEqualTo(null)
		                .isNotEqualTo("foo");
	}

	@Test
	public void t4Combinations() {
		assertThat(new Tuple4<>(1, 2, 3, null))
				.isEqualTo(new Tuple4<>(1, 2, 3, null))
				.isNotEqualTo(new Tuple4<>(1, 2, 3, 10));

		assertThat(new Tuple4<>(1, 2, 3, 4))
				.isNotEqualTo(new Tuple4<>(1, 2, 3, null))
				.isNotEqualTo(new Tuple4<>(1, 2, 3, 10))
				.isEqualTo(new Tuple4<>(1, 2, 3, 4));
	}
}