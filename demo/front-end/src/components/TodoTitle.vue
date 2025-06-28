<template>
  <div class="title">
    <p class="title__text">
      <span class="title__message">Good {{ message }},</span>
      <span
        v-on:keyup.enter="handleEnter"
        v-on:blur="handleBlur"
        class="title__name"
        ref="test"
        contenteditable="true"
      >{{ this.userName }}</span>
      .
    </p>
    <p class="title__task">
      <span class="title__task-top">오늘의 할 일은</span>
      <span class="title__task-count">
        <em class="title__task-left">{{ this.todoItemsCount.left }}</em>
        <em
          v-if="this.todoItemsCount.total"
          class="title__task-total"
        >&nbsp;/ {{ this.todoItemsCount.total }}</em>
      </span>
      <span class="title__task-bottom">
        <span v-if="this.todoItemsCount.total > 1"></span>
        <span v-else></span> 입니다 !
      </span>
      <span class="title__task-info"></span>
    </p>
  </div>
</template>

<script>
import getDate from "../assets/common/getDate.js";
export default {
  data() {
    return {
      //시간대별로 morning, afternoon, evening 출력
      message: "",
      // 저장된 userName 가져옴
      userName: this.$store.getters.storedName
    };
  },
  methods: {
    // 포커스아웃시, 입력된 이름을 userName으로 저장
    handleBlur(e) {
      const originalName = this.userName;
      const newName = e.target.innerText;
      if (newName !== originalName) {
        if (newName === "") {
          e.target.innerText = originalName;
        } else {
          this.$store.commit("setUserName", newName);
        }
      }
    },
    handleEnter() {
      this.$refs.test.blur();
    }
  },
  computed: {
    // 할 일 목록 수 표시
    todoItemsCount() {
      const checkLeftItems = () => {
        const items = this.$store.getters.storedTodoItems;
        let leftCount = 0;
        for (let i = 0; i < items.length; i++) {
          if (items[i].completed === false) {
            leftCount++;
          }
        }
        return leftCount;
      };
      const count = {
        total: this.$store.getters.storedTodoItemsCount,
        left: checkLeftItems()
      };
      return count;
    }
  },
  mounted() {
    this.message = getDate().daytime;
  }
};
</script>

<style lang="scss">
.title {
  max-width: $max-width;
  margin: 0 auto;
  letter-spacing: 0.03rem;
  color: #fff;
  &__text {
    cursor: default;
    font-size: 1.6rem;
    @include flexbox;
    @include align-items(center);
    @include flex-wrap(wrap);
    @include ellipsis();
  }
  .wrap {
    position: relative;
    white-space: nowrap;
    overflow: hidden;
  }
  &__message {
    display: block;
    min-height: 2.7rem;
    margin-right: 0.4rem;
    @include flex-shrink(0);
  }
  &__name {
    display: block;
    background: 0;
    border: 0;
    outline: 0;
    color: inherit;
    font-size: inherit;
    min-width: 1rem;
    min-height: 2.7rem;
    overflow: hidden;
    @include flex-shrink(1);
    br {
      display: none;
    }
    &:hover {
      text-decoration: underline;
      cursor: text;
    }
    &:focus {
      opacity: 0.8;
      // outline: 1px solid #fafafa;
      background: rgba(255, 255, 255, 0.15);
      text-decoration: underline;
    }
  }
  &__task {
    margin-top: 3.5rem;
    margin-bottom: 5rem;
    font-weight: bold;
    &-top {
      display: block;
      font-size: 2.6rem;
    }
    &-count {
      display: block;
      font-size: 5.4rem;
    }
    &-total {
      font-size: 50%;
    }
    &-bottom {
      display: block;
      font-size: 2.8rem;
      line-height: 50%;
    }
  }
}
//애니메이션 관리
.title {
  &__text {
    @include animation(fadeShow, 500ms);
  }
  &__task-top {
    @include animation(fadeShow, 600ms, 1, 400ms);
  }
  &__task-count {
    @include animation(fadeShow, 600ms, 1, 550ms);
  }
  &__task-bottom {
    @include animation(fadeShow, 600ms, 1, 700ms);
  }
}
</style>