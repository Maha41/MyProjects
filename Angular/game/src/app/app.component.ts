import { Component } from '@angular/core';
import { BestScoreManager } from './app.storage.service';
import { CONTROLS, COLORS, BOARD_SIZE, GAME_MODES } from './app.constants';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  host: {
    '(document:keydown)': 'handleKeyboardEvents($event)'
  }
})
export class AppComponent {
 
  private tempDirection: number;
  private default_mode = 'classic';
  private isOver = false;

  public all_modes = GAME_MODES;
  public getKeys = Object.keys;
  public board = [];
  public obstacles = [];
  public score = 0;
  public showMenuChecker = false;
  public Started = false;
  public newBestScore = false;
  public best_score = this.bestScoreService.retrieve();

  private robo = {
    direction:0,
    parts: [
      {
        x: -1,
        y: -1
      }
    ]
  };

  private Target = {
    x: -1,
    y: -1
  };

  constructor(
    private bestScoreService: BestScoreManager
  ) {
    this.setBoard();
  }

  handleKeyboardEvents(e: KeyboardEvent) {
    if (e.keyCode === CONTROLS.LEFT && this.robo.direction !== CONTROLS.RIGHT) {
      this.tempDirection = CONTROLS.LEFT;
    } else if (e.keyCode === CONTROLS.UP && this.robo.direction !== CONTROLS.DOWN) {
      this.tempDirection = CONTROLS.UP;
    } else if (e.keyCode === CONTROLS.RIGHT && this.robo.direction !== CONTROLS.LEFT) {
      this.tempDirection = CONTROLS.RIGHT;
    } else if (e.keyCode === CONTROLS.DOWN && this.robo.direction !== CONTROLS.UP) {
      this.tempDirection = CONTROLS.DOWN;
    }else {
      this.tempDirection = 0;
    }
  }

  setColors(col: number, row: number): string {
    if (this.isOver) {
      return COLORS.DONE;
    } else if (this.Target.x === row && this.Target.y === col) {
      return COLORS.Target;
    } else if (this.robo.parts[0].x === row && this.robo.parts[0].y === col) {
      return COLORS.HEAD;
    } else if (this.board[col][row] === true) {
      return COLORS.BODY;
    } else if (this.default_mode === 'obstacles' && this.checkObstacles(row, col)) {
      return COLORS.OBSTACLE;
    }

    return COLORS.BOARD;
  };

  updatePositions(): void {
    let newHead = this.repositionHead();
    let me = this;

    if (this.default_mode === 'classic' && this.boardCollision(newHead)) {
      return this.Over();
     
    } else if (this.default_mode === 'no_walls') {
      this.noWallsTransition(newHead);
    } else if (this.default_mode === 'obstacles') {
      this.noWallsTransition(newHead);
      if (this.obstacleCollision(newHead)) {
        return this.Over();
      }
    
    }
  

    if (this.selfCollision(newHead)) {
 
    } else if (this.TargetCollision(newHead)) {
      this.reachTarget();
    }

    let oldTail = this.robo.parts.pop();
    this.board[oldTail.y][oldTail.x] = false;

    this.robo.parts.unshift(newHead);
    this.board[newHead.y][newHead.x] = true;

    this.robo.direction = 0;

    setTimeout(() => {
      me.updatePositions();
    });
  }

  repositionHead(): any {
    let newHead = Object.assign({}, this.robo.parts[0]);

    if (this.tempDirection === CONTROLS.LEFT) {
      newHead.x -= 1;
      this.tempDirection = 0;
    } else if (this.tempDirection === CONTROLS.RIGHT) {
      newHead.x += 1;
      this.tempDirection = 0;
    } else if (this.tempDirection === CONTROLS.UP) {
      newHead.y -= 1;
      this.tempDirection = 0;
    } else if (this.tempDirection === CONTROLS.DOWN) {
      newHead.y += 1;
      this.tempDirection = 0;
    }
   
    return newHead;
  }

  noWallsTransition(part: any): void {
    if (part.x === BOARD_SIZE) {
      part.x = 0;
    } else if (part.x === -1) {
      part.x = BOARD_SIZE - 1;
    }

    if (part.y === BOARD_SIZE) {
      part.y = 0;
    } else if (part.y === -1) {
      part.y = BOARD_SIZE - 1;
    }
  }

  addObstacles(): void {
    let x = this.randomNumber();
    let y = this.randomNumber();

    if (this.board[y][x] === true || y === 8) {
      return this.addObstacles();
    }

    this.obstacles.push({
      x: x,
      y: y
    });
  }

  checkObstacles(x, y): boolean {
    let res = false;

    this.obstacles.forEach((val) => {
      if (val.x === x && val.y === y) {
        res = true;
      }
    });

    return res;
  }

  obstacleCollision(part: any): boolean {
    return this.checkObstacles(part.x, part.y);
  }

  boardCollision(part: any): boolean {
    return part.x === BOARD_SIZE || part.x === -1 || part.y === BOARD_SIZE || part.y === -1;
  }

  selfCollision(part: any): boolean {
    return this.board[part.y][part.x] === true;
  }

  TargetCollision(part: any): boolean {
    return part.x === this.Target.x && part.y === this.Target.y;
  }

  resetTarget(): void {
    let x = this.randomNumber();
    let y = this.randomNumber();

    if (this.board[y][x] === true || this.checkObstacles(x, y)) {
      return this.resetTarget();
    }

    this.Target = {
      x: x,
      y: y
    };
  }

  reachTarget(): void {
    this.score++;

    this.resetTarget();

  
  }

  Over(): void {
    this.isOver = true;
    this.Started = false;
    let me = this;

    if (this.score > this.best_score) {
      this.bestScoreService.store(this.score);
      this.best_score = this.score;
      this.newBestScore = true;
    }


    this.setBoard();
  }

  randomNumber(): any {
    return Math.floor(Math.random() * BOARD_SIZE);
  }

  setBoard(): void {
    this.board = [];

    for (let i = 0; i < BOARD_SIZE; i++) {
      this.board[i] = [];
      for (let j = 0; j < BOARD_SIZE; j++) {
        this.board[i][j] = false;
      }
    }
  }

  showMenu(): void {
    this.showMenuChecker = !this.showMenuChecker;
  }

  newGame(mode: string): void {
    this.default_mode = mode || 'classic';
    this.showMenuChecker = false;
    this.newBestScore = false;
    this.Started = true;
    this.score = 0;
    this.tempDirection = CONTROLS.LEFT;
    this.isOver = false;
  
    this.robo = {
      direction: CONTROLS.LEFT,
      parts: []
    };

    for (let i = 0; i < 1; i++) {
      this.robo.parts.push({ x: 8 + i, y: 8 });
    }

    if (mode === 'obstacles') {
      this.obstacles = [];
      let j = 1;
      do {
        this.addObstacles();
      } while (j++ < 9);
    }

    this.resetTarget();
    this.updatePositions();
  }
}
