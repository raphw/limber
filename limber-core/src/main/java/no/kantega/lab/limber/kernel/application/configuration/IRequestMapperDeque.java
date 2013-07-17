package no.kantega.lab.limber.kernel.application.configuration;

import no.kantega.lab.limber.kernel.mapper.IRequestMapper;

import java.util.Deque;

public interface IRequestMapperDeque extends Deque<IRequestMapper>, IRequestMapper {

}
