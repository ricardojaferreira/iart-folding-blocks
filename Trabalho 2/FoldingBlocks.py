import gym
import gym_fb
import numpy as np
import random


def qLearning(env, eps, eps_min, eps_decay, episodes, steps, lr, gamma):
    qtable = np.zeros((300, env.action_space.n))

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
                print("Episode finished after {} timesteps".format(step+1))
                break
            print(info)
            # env.render()

        if eps >= eps_min:
            eps *= eps_decay

        env.render()


env = gym.make("fb-v1")
qLearning(env, 1.0, 0.005, 0.99993, 10, 50, 0.65, 0.65)

env.close()
