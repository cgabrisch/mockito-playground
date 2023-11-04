package de.cgabrisch.mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MockedListTest {
	@Mock
	private List<Object> mockedList;
	
	@Test
	void injectingFieldsWithMockAnnotation() {
		assertNotNull(mockedList);
		assertNull(mockedList.get(37));
	}
	
	@Test
	void stubbingMethods() {
		// A statically imported when() as the first statement is rejected by Eclipse
		// See https://github.com/eclipse-jdt/eclipse.jdt.core/issues/456
		Mockito.when(mockedList.get(37)).thenReturn("Foo");
		
		assertNull(mockedList.get(36));
		assertEquals("Foo", mockedList.get(37));
		assertNull(mockedList.get(38));
	}
	
	@Test
	void verifyingMockInteractions() {
		Mockito.when(mockedList.get(37)).thenReturn("Foo");

		for (int i = 0; i < 100; i++) {
			mockedList.get(i);
		}
		
		List.of(times(1), atLeastOnce(), atMostOnce()).forEach(
				verificationMode -> verify(mockedList, verificationMode).get(37));
		
		verify(mockedList, times(100)).get(anyInt());
	}

}
