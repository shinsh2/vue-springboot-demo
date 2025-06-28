<template>
  <transition-group name="list" tag="ul" class="list" v-bind:class="listempty">
    <li
      class="list__item"
      v-for="(todoItem, index) in this.storedTodoItems"
      v-bind:key="todoItem.item"
    >
      <input
        type="checkbox"
        v-bind:id="todoItem.item"
        v-bind:checked="todoItem.completed === true"
        v-on:change="toggleComplete({todoItem})"
      />
      <label v-bind:for="todoItem.item" class="list__label">
        <span class="icon-check"></span>
        <p class="list__text">{{ todoItem.item }}</p>
      </label>
      <div class="list__right">
        <button class="list__delete" v-on:click="removeTodo({todoItem, index})">
          <div class="blind">Delete</div>
        </button>
        <p class="list__date">{{ todoItem.date }}</p>
      </div>
    </li>
  </transition-group>
</template>

<script>
import {mapGetters} from "vuex";
import {mapMutations} from "vuex";

export default {
  name: 'todoItems',
  computed: {
    ...mapGetters(["storedTodoItems", "storedTodoItemsCount"]),
    listempty() {
      return this.storedTodoItemsCount <= 0 ? "list--empty" : null;
    }
  },
  methods: {
    ...mapMutations({
      removeTodo: "removeOneItem",
      toggleComplete: "toggleOneItem"
    }),
  },
};

</script>

<style lang="scss">
.list {
  position: relative;
  max-width: $max-width;
  margin: 2.1rem auto 0;
  &:after {
    opacity: 0;
    display: block;
    text-align: center;
    font-size: 2.2rem;
    color: #c4c4c4;
    letter-spacing: 0.4rem;
    content: "ଘ(੭˘꒳˘)੭✧";
    transition: opacity 0s;
  }
  &__item {
    position: relative;
    width: 100%;
    padding: 0 1.4rem 0 4.5rem;
    background-color: #fff;
    border-radius: 4px;
    @include flexbox;
    @include align-items(center);
    &:not(:first-child) {
      margin-top: 1.2rem;
    }
  }
  input {
    @include invisible;
    &:checked + label {
      .icon-check:before {
        height: 0.74rem;
      }
      &:after {
        opacity: 1;
      }
      .list__text {
        color: #bababa;
        text-decoration: line-through;
        // &:before {
        //   width: 100%;
        //   transition: width 0.3s;
        // }
      }
    }
  }
  &__label {
    padding: 1.6rem 0;
    font-size: 1.65rem;
    line-height: 1.24;
    letter-spacing: 0.017rem;
    color: #141313;
    min-width: 0;
    flex-basis: 100%;
    cursor: pointer;
    &:before {
      position: absolute;
      top: 50%;
      left: 1.4rem;
      transform: translateY(-50%);
      display: inline-block;
      width: 2rem;
      height: 2rem;
      border: solid 1px #818181;
      background-color: #ffffff;
      content: "";
      cursor: pointer;
    }
    &:after {
      opacity: 0;
      position: absolute;
      display: block;
      width: 2rem;
      height: 2rem;
      top: 50%;
      left: 1.4rem;
      transform: translateY(-50%);
      background-size: 1.4rem 1.4rem;
      background-position: 50% 50%;
      background-repeat: no-repeat;
      background-image: icon("check", "363636");
      transition: opacity 0.15s;
      content: "";
    }
  }
  &__text {
    position: relative;
    width: 100%;
    @include ellipsis(1);
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    transition: color 0.15s;
  }
  &__right {
    min-width: 5rem;
    margin-left: 1.3rem;
    text-align: right;
  }
  &__delete {
    position: relative;
    width: 1rem;
    height: 1rem;
    padding: 0.4rem;
    padding-right: 0;
    box-sizing: content-box;
    margin-left: auto;
    margin-bottom: 0.3rem;
    &:before,
    &:after {
      top: 0.2rem;
      right: 0.4rem;
      position: absolute;
      display: block;
      content: "";
      width: 0.1rem;
      height: 1.2rem;
      background-color: rgb(184, 184, 184);
      transition: background-color 0.15s;
    }
    &:before {
      transform: rotate(-40deg);
    }
    &:after {
      transform: rotate(40deg);
    }
    &:hover {
      &:before,
      &:after {
        background-color: #343434;
      }
    }
  }
  &__date {
    font-size: 1.1rem;
    color: #939393;
    letter-spacing: 0.02rem;
  }
  &.list--empty {
    &:after {
      opacity: 1;
      margin: 11rem 0 12rem;
      animation: emptyAnimation 1s 1s;
      transition: opacity 1s 0.3s;
    }
  }
}
@keyframes emptyAnimation {
  100% {
    transform: translate3d(0, 0, 0) rotateY(360deg);
  }
}
// ==== 트랜지션
.list-enter {
  opacity: 0;
  transform: translateX(-30px);
}
.list-enter-active,
.list-leave-active {
  transition: transform 0.5s, opacity 0.5s;
}
.list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>