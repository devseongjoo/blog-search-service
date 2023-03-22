public class EntityTest {
    @Test
    @Transactional
    public void SaveUpdateTest(){
        Entity en = new Entity();
        en.A = 1;
        // 첫번째 save
        entityRepository.save(en);
        Entity en2 = entityRepository.findByA(1);
        en2.A = 2;
        entityRepository.save(en2);

        assertThat(entityRepository.findByA(1)).isEqualTo(2);
    }
}
