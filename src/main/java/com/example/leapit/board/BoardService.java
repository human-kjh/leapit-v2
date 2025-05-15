package com.example.leapit.board;

import com.example.leapit.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponse.DTO save(BoardRequest.SaveDTO reqDTO, User sessionUser) {
        Board board = reqDTO.toEntity(sessionUser);
        Board boardPS = boardRepository.save(board);
        return new BoardResponse.DTO(boardPS);
    }
}
