package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BeerRepository beerRepository;

    @Transactional
    @Test
    void testCategoryRepository() {
        Category category = Category.builder()
                .description("no alcohol")
                .build();

        var testBeer = beerRepository.findAll().get(0);
        testBeer.addCategory(category);

        var savedBeer = beerRepository.save(testBeer);

        var savedCategory = categoryRepository.save(category);

        assertThat(savedBeer.getCategories().size()).isEqualTo(1);
        assertThat(savedCategory.getBeers().size()).isEqualTo(1);
    }

}