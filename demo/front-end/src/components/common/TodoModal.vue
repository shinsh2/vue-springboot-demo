<template>
  <!-- <transition name="modal">
    <div class="modal">
      <div class="modal__dim">
        <div class="modal__container">
          <div class="modal__content">
            <p class="modal__text">
              <slot name="modal-text">(여기에 모달 내용이 들어가요)</slot>
            </p>
            <button class="modal__close" v-on:click="$emit('close')">OK</button>
          </div>
        </div>
      </div>
    </div>
  </transition>-->

  <transition name="modal" appear>
    <div class="modal modal__dim" v-on:click.self="$emit('close')">
      <div class="modal__content">
        <p class="modal__text">
          <slot name="modal-text">(여기에 모달 내용이 들어가요)</slot>
        </p>
        <button class="modal__close" v-on:click="$emit('close')">OK</button>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  // props: {
  //   visible: {
  //     type: Boolean,
  //     default: false
  //   },
  //   text: {
  //     type: String
  //   }
  // }
};
</script>

<style lang="scss">
.modal {
  &__dim {
    @include flexbox;
    @include justify-content(center);
    @include align-items(center);
    position: fixed;
    z-index: 999;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
  }
  &__content {
    min-width: 34rem;
    margin: 0px auto;
    padding: 3rem;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 10px 30px -4px rgba(10, 10, 10, 0.2);
    transition: all 0.3s ease;
  }
  &__text {
    text-align: center;
    font-size: 1.4rem;
    color: #4a4a4a;
    &:before {
      display: block;
      margin: 0 auto;
      // width: 5rem;
      // height: 5rem;
      // border: 2px solid red;
      // background-color: #f0f0f0;
      // border-radius: 50%;
      text-align: center;
      line-height: 5rem;
      font-size: 2.2rem;
      font-weight: bold;
      // content: "Σ(゜ロ゜;)";
      content: "Oops !";
    }
  }
  &__close {
    text-align: center;
    margin: 2.5rem auto 0;
    padding: 0.4rem 1rem;
    border: 1px solid #b1b1b1;
    border-radius: 0.2rem;
    font-size: 1.2rem;
    color: #555555;
    &:hover {
      color: #323232;
      border-color: #4a4a4a;
    }
  }
}
// ---- 트랜지션 정의 ----
.modal-enter {
  opacity: 0;
  .modal__content {
    transform: translateY(30px);
    transition: transform 0.6s;
  }
}
.modal-enter-active {
  transition: opacity 0.3s;
}
.modal-leave-active {
  transition: opacity 0.3s 0.05s;
}
.modal-leave-to {
  opacity: 0;
  .modal__content {
    transform: translateY(30px);
    transition: transform 0.6s;
  }
}
</style>