package co.com.ingeniods.shared.infrastructure.repository.assembler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import co.com.ingeniods.shared.infrastructure.repository.dto.BaseDto;
import co.com.ingeniods.shared.modules.domain.Entity;

public interface DtoAssembler<E extends Entity, D extends BaseDto> {

	E toEntity(D dto);

	D toDto(E entity);

	public default List<E> toEntity(Iterable<D> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false).map(this::toEntity).collect(Collectors.toList());
	}
	
	public default List<D> toDto(Iterable<E> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false).map(this::toDto).collect(Collectors.toList());
	}

}
