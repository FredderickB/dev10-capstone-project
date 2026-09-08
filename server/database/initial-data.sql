INSERT INTO game (user_id, engine_level, fen, status_id, player_color_id, created_at, board_peaks) VALUES
(1, 1500, 'rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e3 0 2', 1, 1, NOW() - INTERVAL 10 MINUTE, 1),
(1, 1200, 'r1bqkb1r/pppp1ppp/2n2n2/4p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR w KQkq - 4 4', 2, 1, NOW() - INTERVAL 1 DAY, 0),
(1, 1800, 'rnb1kbnr/pppp1ppp/8/4p3/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 1 3', 3, 2, NOW() - INTERVAL 2 DAY, 3),
(1, 1400, '8/8/8/4k3/8/8/8/4K3 w - - 0 50', 4, 1, NOW() - INTERVAL 3 DAY, 0),
(1, 1500, '7k/5Q2/6K1/8/8/8/8/8 b - - 0 1', 5, 2, NOW() - INTERVAL 4 DAY, 2),
(1, 1600, 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 50 25', 6, 1, NOW() - INTERVAL 5 DAY, 1);

insert into move (game_id, move_number, move_san, fen_after) values 
		(1, 1, "e4", "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"),
		(1, 2, "e5", "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e3 0 2");
