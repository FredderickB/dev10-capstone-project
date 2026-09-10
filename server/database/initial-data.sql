use blind_chess;

delete from move;
alter table move auto_increment = 1;
delete from game;
alter table game auto_increment = 1;

INSERT INTO game (user_id, engine_level, fen, status_id, player_color_id, created_at, board_peaks) VALUES
(1, 1600, 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 50 25', 6, 1, NOW() - INTERVAL 5 DAY, 1),
(1, 1500, '7k/5Q2/6K1/8/8/8/8/8 b - - 0 43', 5, 2, NOW() - INTERVAL 4 DAY, 2),
(1, 1400, '8/8/8/4k3/8/8/8/4K3 w - - 0 50', 4, 1, NOW() - INTERVAL 3 DAY, 0),
(1, 1800, 'rnb1kbnr/pppp1ppp/8/4p3/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 1 14', 3, 2, NOW() - INTERVAL 2 DAY, 3),
(1, 1200, 'r1bqkb1r/pppp1ppp/2n2n2/4p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR w KQkq - 4 23', 2, 1, NOW() - INTERVAL 1 DAY, 0),
(1, 2500, 'rnbqkbnr/1p2pppp/p2p1n2/8/3NP3/2N5/PPP2PPP/R1BQKB1R w KQkq - 0 6', 1, 1, NOW() - INTERVAL 10 MINUTE, 1);

insert into move (game_id, move_number, move_san, fen_after) values 
	(6, 1, 'e4', 'rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1'),
	(6, 2, 'c5', 'rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2'),
	(6, 3, 'Nf3', 'rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2'),
	(6, 4, 'd6', 'rnbqkbnr/pp2pppp/3p4/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 0 3'),
	(6, 5, 'd4', 'rnbqkbnr/pp2pppp/3p4/2p5/3PP3/5N2/PPP2PPP/RNBQKB1R b KQkq - 0 3'),
	(6, 6, 'cxd4', 'rnbqkbnr/pp2pppp/3p4/8/3nP3/5N2/PPP2PPP/RNBQKB1R w KQkq - 0 4'),
	(6, 7, 'Nxd4', 'rnbqkbnr/pp2pppp/3p4/8/3NP3/8/PPP2PPP/RNBQKB1R b KQkq - 0 4'),
	(6, 8, 'Nf6', 'rnbqkbnr/pp2pppp/3p1n2/8/3NP3/8/PPP2PPP/RNBQKB1R w KQkq - 1 5'),
	(6, 9, 'Nc3', 'rnbqkbnr/pp2pppp/3p1n2/8/3NP3/2N5/PPP2PPP/R1BQKB1R b KQkq - 2 5'),
	(6, 10, 'a6', 'rnbqkbnr/1p2pppp/p2p1n2/8/3NP3/2N5/PPP2PPP/R1BQKB1R w KQkq - 0 6');
