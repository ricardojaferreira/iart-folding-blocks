import gym
from gym import error, spaces, utils
from gym.utils import seeding
from gym_fb.envs.fb_board import fb_board
import numpy as np


class FoldingBlocks(gym.Env):
    metadata = {'render.modes': ['human']}

    def __init__(self):
        self.actions = ["DBUP", "DBDOWN", "DBLEFT", "DBRIGHT"]

        self.boardLayout = [
            [1, 1, 4, 0, 0],
            [1, 1, 4, 0, 0],
            [1, 1, 4, 0, 0],
            [1, 1, 1, 0, 0],
            [1, 1, 1, 0, 0],
            [1, 1, 1, 0, 0],
            [1, 1, 1, 1, 1],
            [2, 1, 1, 1, 5]
        ]
        self.state = fb_board(self.boardLayout)
        self.done = False
        self.invalidMove = False
        self.reward = 0
        self.action_space = spaces.Discrete(self.state.blockCount * 4-1)

    def checkCompletion(self):
        for row in self.state.cells:
            for rowCell in row:
                if rowCell.id == 1:
                    return False
        return True

    def step(self, target):
        info = {}

        if self.done == True:
            return self.state, self.reward, self.done, info

        blockVal = target // (self.state.blockCount + 1)
        block = self.state.blocks[blockVal]
        actionVal = target % 4
        action = self.actions[actionVal]

        info = {block.color, action}

        dup = self.state.duplicateBlock(block.id, action)

        if dup:
            self.reward -= 1
            if self.checkCompletion():
                self.done = True
        else:
            self.invalidMove = True

        return self.state, self.reward, self.done, info

    def reset(self):
        self.state = fb_board(self.boardLayout)
        self.done = False
        self.reward = 0

    def render(self):
        print()
        self.state.print()
        print()

    def seed(self, seed=None):
        self.np_random, seed = seeding.np_random(seed)
        return [seed]
