import gym
import gym_fb
import numpy as np
from matplotlib import pyplot as plt
import random


def loadQTableCSV(filepath):
    return np.genfromtxt(filepath, delimiter=',')


def saveQTableCSV(qtable, filepath):
    np.savetxt(filepath, qtable, delimiter=",")


def qLearning(env, qtable, eps, eps_min, eps_decay, episodes, steps, lr, gamma):

    stepStats = np.zeros(episodes)

    for ep in range(episodes):
        state = env.reset()
        done = False
        for step in range(steps):
            if random.uniform(0, 1) > eps:
                action = int(np.argmax(qtable[state, :]))
            else:
                action = env.action_space.sample()  # take a random action

            new_state, reward, done, info = env.step(action)

            old_value = qtable[state, action]
            next_max = np.max(qtable[new_state, :])

            new_value = (1 - lr) * old_value + \
                lr * (reward + gamma * next_max)

            qtable[state, action] = new_value

            state = new_state

            if done:
                stepStats[ep] = step+1
                # print("Episode finished after {} timesteps".format(step+1))
                break
            # print(info)
            # env.render()

        if eps >= eps_min:
            eps *= eps_decay

        # env.render()
    return stepStats


env = gym.make("fb-v1")  # Create environment

qtable = np.zeros((2000, env.action_space.n))  # Generate new QTable
# qtable = loadQTableCSV("data/QTable.csv") # Load saved QTable

stepStats = qLearning(env, qtable, 1.0, 0.005, 0.995, 1000, 100, 0.65, 0.65)

# saveQTableCSV(qtable, "data/QTable.csv")  # Save QTable to file

env.close()

plt.autoscale()
plt.plot(stepStats)
plt.xlabel("Episode Number")
plt.ylabel("Steps to completion/failure")
plt.show()
