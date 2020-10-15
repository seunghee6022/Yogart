<template>
    <div v-if="exerciseHistory" class="pt-3">
        <GraphView></GraphView>
    </div>
</template>

<script>
import GraphView from '@/views/mypage/GraphView.vue'

export default {
    name : 'ExerciseHistory',
    props : {
        exerciseHistory : Boolean,
    },
    components: {
        GraphView,
    }
}
</script>

<style>

</style>